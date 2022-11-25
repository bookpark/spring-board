package mini.boardapi.service;

import lombok.RequiredArgsConstructor;
import mini.boardapi.domain.Board;
import mini.boardapi.repository.BoardRepository;
import mini.boardapi.vo.PageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public void updateBoard(Long id, String subject, String content) throws Exception {
        Optional<Board> oBoard = boardRepository.findById(id);
        if (oBoard.isEmpty()) {
            throw new Exception("글 조회 오류");
        }
        Board board = oBoard.get();
        board.setSubject(subject);
        board.setContent(content);
        boardRepository.save(board);
    }

    public List<Board> listBoard() {
        return boardRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
    }

    public List<Board> pageBoard(PageInfo pageInfo) throws Exception {
        PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage() - 1, 5,
                Sort.by(Sort.Direction.DESC, "id"));
        Page<Board> pages = boardRepository.findAll(pageRequest);

        int maxPage = pages.getTotalPages();
        // 정수의 값을 가져오기 위해 10으로 나눈 후 다시 10을 곱해줌 (시작페이지는 11, 21, 31... 이기 때문에 마지막에 +1을 해줌)
        int startPage = pageInfo.getCurPage() / 10 * 10 + 1;
        int endPage = startPage + 10 - 1; // 20, 30, 40...
        if (endPage > maxPage) {
            endPage = maxPage;
        }
        pageInfo.setAllPage(maxPage);
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);

        return pages.getContent();
    }

}
