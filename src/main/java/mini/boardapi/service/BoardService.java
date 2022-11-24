package mini.boardapi.service;

import lombok.RequiredArgsConstructor;
import mini.boardapi.domain.Board;
import mini.boardapi.repository.BoardRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void writeBoard(Board board) throws Exception {
        boardRepository.save(board);
    }

    public void writeBoardV2(Board board, MultipartFile file) throws Exception {
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

    public Board detailBoard(Long id) throws Exception {
        Optional<Board> board = boardRepository.findById(id);
        if (board.isPresent()) {
            return board.get();
        }
        throw new Exception("글 번호 오류");
    }

    public List<Board> listBoard() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }
}
