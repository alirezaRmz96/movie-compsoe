package com.example.moviecleenapp.ui.screens.detials

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.movie.R
import com.example.moviecleenapp.data.components.HorizontalMovieCategory
import com.example.moviecleenapp.data.model.Result
import com.example.moviecleenapp.data.units.getMovieCategory
import com.example.moviecleenapp.ui.theme.Background
import com.example.moviecleenapp.ui.theme.buttonColor
import com.example.moviecleenapp.ui.viewModel.MainViewModel
import kotlinx.coroutines.launch


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@SuppressLint("UnrememberedMutableState")
@Composable
fun DetailsScreen(
    navController: NavController,
    movie: Result,
    viewModel: MainViewModel = hiltViewModel()
) {
    val bottomSheetItems = listOf(
        BottomSheetItem(title = "WHATS APP", image = R.drawable.whatsup),
        BottomSheetItem(title = "GOOGLE", image = R.drawable.google),
        BottomSheetItem(title = "INSTAGRAM", image = R.drawable.instagram),
        BottomSheetItem(title = "TELEGRAM", image = R.drawable.telegram),
    )
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    val coroutineScope = rememberCoroutineScope()
    val isFavorite = remember { mutableStateOf(movie.favoriteMovies) }
    BottomSheetScaffold(
        backgroundColor = Background,
        scaffoldState = bottomSheetScaffoldState,
        sheetContent = {
            Column(
                content = {
                    Spacer(modifier = Modifier.padding(10.dp))
                    Text(
                        text = "Pick one",
                        modifier = Modifier
                            .fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        color = Color.White,
                        fontFamily = FontFamily.Cursive
                    )
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(2)
                    ) {
                        items(bottomSheetItems.size,
                            itemContent = {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 24.dp)
                                ) {
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    Image(
                                        painterResource(id =bottomSheetItems[it].image),
                                        "",
                                        modifier = Modifier.clickable {
                                            Log.d("TAG", "DetailsScreen:${1} ")
                                        }
                                    )
                                    Spacer(modifier = Modifier.padding(8.dp))
                                    Text(
                                        text = bottomSheetItems[it].title,
                                        color = Color.White,
                                        fontFamily = FontFamily.SansSerif,
                                        fontSize = 15.sp
                                    )
                                }

                            })
                    }
                          },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Background)
                    .padding(16.dp)
            )
        },
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(20.dp),
        sheetPeekHeight = 0.dp,
        sheetElevation = 10.dp,
    ) {
        Log.d("TAG", " in DetailsScreen: " +movie)

        Column() {
            val scrollState = rememberScrollState()

            Column(modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 40.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                )
                {
                    BackAndShareButton(
                        icon = Icons.Filled.ArrowBack,
                    ){
                        navController.popBackStack()
                    }
                    Text(
                        text = "Details",
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        modifier = Modifier.align(
                            alignment = Alignment.CenterVertically),
                        fontSize = 23.sp,
                        fontFamily = FontFamily.Serif
                    )
                    BackAndShareButton(
                        icon = Icons.Filled.Share,

                    ){
                        coroutineScope.launch{
                           if (bottomSheetScaffoldState.bottomSheetState.isCollapsed){
                               bottomSheetScaffoldState.bottomSheetState.expand()
                           }else{
                               bottomSheetScaffoldState.bottomSheetState.collapse()
                           }
                        }
                    }

                }
                Surface(
                    modifier = Modifier
                        .padding(start = 30.dp, end = 30.dp, top = 20.dp)
                        .fillMaxWidth()
                        .height(400.dp),
                    shape = RoundedCornerShape(17.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(
                            data = "https://image.tmdb.org/t/p/w500" + movie.poster_path,
                            builder = {
                                crossfade(true)
                            }),
                        contentDescription = "Movie Poster",
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Text(
                        text = movie.original_title,
                        fontFamily = FontFamily.Cursive,
                        fontSize = 30.sp,
                        color = Color.White,
                        modifier = Modifier.width(200.dp),
                        maxLines = 2
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            tint = if (isFavorite.value){
                                Color.Red
                            }else{
                                Color.White
                            },
                            modifier = Modifier
                                .padding(end = 5.dp)
                                .clickable {
                                    isFavorite.value  = !isFavorite.value
                                    viewModel.update(movie)
                                    if (isFavorite.value){
                                        viewModel.addMovie(movie)
                                    }
                                    else{
                                        viewModel.deleteFav(movie)
                                    }

                                },
                            contentDescription = "favorite",
                        )
                        Text(
                            text = movie.vote_average.toString(),
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = FontFamily.Cursive
                        )
                    }

                }
                Spacer(modifier = Modifier.height(2.dp))
                HorizontalMovieCategory(movieCategory = getMovieCategory())

                Text(
                    text = movie.title,
                    fontFamily = FontFamily.Cursive,
                    fontSize = 24.sp,
                    color = Color.White,
                    modifier = Modifier.padding(start = 30.dp)
                )

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = movie.overview,
                    modifier = Modifier.padding(start = 30.dp, end = 30.dp),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 13.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(5.dp))
                Box(
                    Modifier
                        .wrapContentSize(Alignment.BottomCenter)
                ) {
                    Button(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(end = 30.dp, start = 30.dp, bottom = 20.dp)
                            .height(60.dp),
                        onClick = {

                        },
                        shape = RoundedCornerShape(13.dp)
                    ) {
                        Text(text = "BUY TICKET")
                    }
                }
            }
        }
    }

}

data class BottomSheetItem(val title: String, val image: Int)


@SuppressLint("UnrememberedMutableState")
@Composable
private fun BackAndShareButton(
    icon:ImageVector,
    onItemClick:  () -> Unit = {},
    ) {
    val showPopMenu = remember { mutableStateOf(false)}
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopCenter)
    )
    {
        Button(
            modifier = Modifier.height(height = 60.dp),
            onClick = {
                onItemClick()
                showPopMenu.value = true
            },
            shape = RoundedCornerShape(13.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = buttonColor
            ),

            ) {
            Image(
                imageVector = icon,
                contentDescription = "arrow back",
                colorFilter = ColorFilter.tint(Color.White)
            )
        }
        
    }
}







