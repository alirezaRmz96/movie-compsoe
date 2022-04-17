package com.example.moviecleenapp.data.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.moviecleenapp.data.model.Result
import com.example.moviecleenapp.ui.theme.Background
import com.example.moviecleenapp.ui.theme.buttonColor
import com.example.moviecleenapp.ui.theme.buttonSelected

@Composable
fun SearchView(state: MutableState<TextFieldValue>){
    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(
                    onClick = {
                        state.value =
                            TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                    }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Clean Test Icon",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        singleLine = true,
        shape = CircleShape,
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = buttonColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}
@Composable
fun MovieTopBar(
    title:String,
    size : TextUnit,
    icon:ImageVector? = null,
    showProfile:Boolean = false,
    navController: NavController,
    onBackArrowClicked:() -> Unit ={}
){
    TopAppBar(title = {
        Row(verticalAlignment = Alignment.CenterVertically){
            if (showProfile) {
                Icon(imageVector = Icons.Default.Face,
                    contentDescription = "Logo Icon",
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .scale(0.9f)
                )

            }
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = "arrow back",
                    tint = Color.Red.copy(alpha = 0.7f),
                    modifier = Modifier.clickable { onBackArrowClicked.invoke() })
            }
            Spacer(modifier = Modifier.width(40.dp) )
            Text(text = title,
                color = Color.Red.copy(alpha = 0.7f),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = size))


        }


    },
        actions = {
            // do some things
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp)
}

@Composable
fun TitleSection(
    modifier: Modifier = Modifier,
    label: String) {
    Surface(
        modifier = Modifier.padding(start = 5.dp, top = 8.dp, bottom = 8.dp),
            color = Background
    ) {
        Column {
            Text(text = label,
                fontSize = 25.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

    }

}
@Composable
fun CategoryRow(category: String) {
    val selected = remember {
        mutableStateOf(true)
    }
    Button(
        onClick = { },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (selected.value) buttonColor
            else buttonSelected
        ),
        modifier = Modifier.padding(10.dp),
        shape = RoundedCornerShape(corner = CornerSize(14.dp))
    )
    {
        Text(
            text = category,
            modifier = Modifier.clickable {
                selected.value = !selected.value
            },
            color = Color.White
        )
    }
}
@Composable
fun ListCard(
    movie : Result,
    onItemClick:(Result) -> Unit
){

    Card(
        modifier = Modifier
            .padding(6.dp)
            .height(242.dp)
            .width(202.dp)
            .clickable {
                onItemClick(movie)
            },
        shape = CircleShape,
        //backgroundColor = Background,

    ) {

        Image(
            painter = rememberImagePainter(
                data = "https://image.tmdb.org/t/p/w500" + movie.poster_path,

            ),
            contentScale = ContentScale.Crop,
            contentDescription = "Image Poster",
            //modifier = Modifier.height(140.dp).width(100.dp).padding(4.dp)
        )
    }
}
@Composable
fun ShimmerAnimation(
) {
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1200, easing = FastOutSlowInEasing),
            RepeatMode.Reverse
        )
    )


    val brush = Brush.linearGradient(
        colors = ShimmerColorShades,
        start = Offset(10f, 10f),
        end = Offset(translateAnim, translateAnim)
    )

    ShimmerItem(brush = brush)
}
val ShimmerColorShades = listOf(

    Color.LightGray.copy(0.9f),

    Color.LightGray.copy(0.2f),

    Color.LightGray.copy(0.9f)

)
@Composable
fun ShimmerItem(brush: Brush) {

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .size(100.dp)
                .background(brush = brush)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(30.dp)
                .padding(vertical = 8.dp)
                .background(brush = brush)
        )
    }
}
@Composable
fun HorizontalMovieCategory( movieCategory: List<String>) {
    LazyRow{
        items(movieCategory){ category->
            CategoryRow(category)
        }
    }
}



