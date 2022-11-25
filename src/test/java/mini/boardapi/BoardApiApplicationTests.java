package mini.boardapi;

import mini.boardapi.domain.Board;
import mini.boardapi.repository.BoardRepository;
import mini.boardapi.service.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@SpringBootTest
class BoardApiApplicationTests {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardService boardService;

    @Test
    void contextLoads() {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Board> pages = boardRepository.findAll(pageRequest);
        List<Board> boards = pages.getContent();
        for (Board board : boards) {
            System.out.println(board);
        }
    }

//    @Test
//    void testJpa() {
//        for (int i = 1; i <= 300; i++) {
//            String writer = "부 기";
//            String password = "1234";
//            String subject = String.format("테스트 데이터입니다:[%03d]", i);
//            String content = "내 용 무";
//            // author가 추가되어 오류 임시해결을 위해 null 추가
//            this.boardService.writeBoardV2(new Board(null, writer, password, subject, content, null), null);
//        }
//    }

}
