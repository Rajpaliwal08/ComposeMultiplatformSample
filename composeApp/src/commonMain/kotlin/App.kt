import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import model.BirdImage
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val greeting = remember { Greeting().greet() }
        LaunchedEffect(Unit) {
            println(getImages())
        }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!, and see the magic")
            }
            AnimatedVisibility(showContent) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    KamelImage(
                        asyncPainterResource(
                            "https://sebi.io/demo-image-api/pigeon/nandkumar-patel-RPUD-n9V6E0-unsplash.jpg")
                            ,"PIGEON"
                    )
                }
            }
        }
    }
}

val httpClient = HttpClient {
    install(ContentNegotiation) {
        json()
    }
}
suspend fun getImages() : List<BirdImage> {
    val images = httpClient
        .get("https://sebi.io/demo-image-api/pictures.json")
        .body<List<BirdImage>>()
    return images
}