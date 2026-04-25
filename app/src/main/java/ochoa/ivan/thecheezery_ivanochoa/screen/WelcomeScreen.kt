package ochoa.ivan.thecheezery_ivanochoa.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ochoa.ivan.thecheezery_ivanochoa.R
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.Brighter_Pink
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.Purple_grey
import ochoa.ivan.thecheezery_ivanochoa.ui.theme.TheCheezery_IvanOchoaTheme

@Composable
fun WelcomeScreen(innerPadding: PaddingValues, onStart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Purple_grey)
            .padding(innerPadding)
    ) {
        Image(
            painter = painterResource(R.drawable.the_cheezery),
            contentDescription = "The Cheezery",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.68f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.32f)
                .background(Purple_grey)
                .padding(horizontal = 18.dp, vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to The Cheezery",
                color = androidx.compose.ui.graphics.Color.White,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = "Home of the most wonderful desserts ever seen, and tasted, by the human being.",
                color = androidx.compose.ui.graphics.Color.White,
                fontSize = 12.sp,
                fontStyle = FontStyle.Italic,
                lineHeight = 15.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(26.dp))
            Button(
                onClick = onStart,
                colors = ButtonDefaults.buttonColors(containerColor = Brighter_Pink)
            ) {
                Text("Get started!", fontSize = 11.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    TheCheezery_IvanOchoaTheme {
        WelcomeScreen(innerPadding = PaddingValues(), onStart = {})
    }
}
