package mini.boardapi.service;

import lombok.RequiredArgsConstructor;
import mini.boardapi.domain.Board;
import mini.boardapi.repository.BoardRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void writeBoard(Board board) throws Exception {
        boardRepository.save(board);
    }

    public void writeBoardV2(Board board) throws Exception {
        MultipartFile file = board.getFile();
        String filename = null;
        if (file != null && !file.isEmpty()) {
            String path = "/Users/book/KFQ/java/spring/board-api/uploads/";
            filename = file.getOriginalFilename();
            File dFile = new File(path + filename);
            file.transferTo(dFile);
            board.setFilename(filename);
        }
        boardRepository.save(board);
    }

}
