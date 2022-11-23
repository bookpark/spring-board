package mini.boardapi.service;

import lombok.RequiredArgsConstructor;
import mini.boardapi.domain.Board;
import mini.boardapi.repository.BoardRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void writeBoard(Board board) throws Exception {
        boardRepository.save(board);
    }

}
