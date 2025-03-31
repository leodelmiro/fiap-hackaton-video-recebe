package utils

import com.leodelmiro.recebevideo.core.domain.Video
import org.springframework.mock.web.MockMultipartFile
import java.nio.file.Files
import java.nio.file.Paths

fun criaVideo(): Video {
    val resourcePath = "src/test/resources/video.mp4"
    val fileBytes = Files.readAllBytes(Paths.get(resourcePath))

    val multipartFile = MockMultipartFile(
        "arquivo",
        "test-video.mp4",
        "video/mp4",
        fileBytes
    )

    return Video(
        nome = "Test",
        descricao = "Test Descricao",
        autor = "Testador",
        arquivo = multipartFile
    )
}