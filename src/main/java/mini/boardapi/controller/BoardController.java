package mini.boardapi.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import mini.boardapi.domain.Board;
import mini.boardapi.service.BoardService;
import mini.boardapi.vo.PageInfo;
import org.aspectj.util.FileUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/api/write-board")
    public ResponseEntity<String> writeBoard(@RequestParam("writer") String writer,
                                             @RequestParam("password") String password,
                                             @RequestParam("subject") String subject,
                                             @RequestParam("content") String content,
                                             @RequestParam(name = "file", required = false) MultipartFile file) {
        ResponseEntity<String> res = null;
        try {
            String filename = null;
            if (file != null && !file.isEmpty()) {
                String path = "/Users/book/KFQ/java/";
                filename = file.getOriginalFilename();
                File dFile = new File(path + filename);
                file.transferTo(dFile);
            }
            boardService.writeBoard(new Board(null, writer, password, subject, content, filename));
            res = new ResponseEntity<String>("게시글 저장 성공", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("게시글 저장 실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @PostMapping("/api/write-board-v2")
    public ResponseEntity<String> writeBoardV2(@ModelAttribute Board board,
                                               @RequestParam(name = "file", required = false) MultipartFile file) {
        ResponseEntity<String> res = null;
        try {
            boardService.writeBoardV2(board, file);
            res = new ResponseEntity<String>("게시글 저장 성공", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("게시글 저장 실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @GetMapping("/api/board-detail/{id}")
    public ResponseEntity<Board> boardDetail(@PathVariable Long id) {
        ResponseEntity<Board> res = null;
        try {
            Board board = boardService.detailBoard(id);
            res = new ResponseEntity<Board>(board, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<Board>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @GetMapping("/img/{filename}")
    public void imageView(@PathVariable String filename, HttpServletResponse response) {
        System.out.println(filename);
        try {
            String path = "/Users/book/KFQ/java/spring/board-api/uploads/";
            FileInputStream fis = new FileInputStream(path + filename);
            OutputStream out = response.getOutputStream();
            FileCopyUtils.copy(fis, out);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/api/list")
    public ResponseEntity<List<Board>> listBoard() throws Exception {
        ResponseEntity<List<Board>> res = null;
        List<Board> boards = null;
        try {
            boards = boardService.listBoard();
            res = new ResponseEntity<List<Board>>(boards, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<List<Board>>(boards, HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @PutMapping("/api/update/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable Long id,
                                              @RequestParam("subject") String subject,
                                              @RequestParam("content") String content) {
        ResponseEntity<String> res = null;
        try {
            Board board = boardService.detailBoard(id);
            boardService.updateBoard(id, subject, content);
            res = new ResponseEntity<String>("게시글 수정 성공", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<String>("게시글 수정 실패", HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    // 400이 뜨는 경우는 mapping이 안됐거나 parameter가 안맞거나
    @GetMapping(value = {"/page/{page}", "/page"})
    public ResponseEntity<Map<String, Object>> pageBoard(@PathVariable(required = false) Integer page) {
        if (page == null) {
            page = 1;
        }
        ResponseEntity<Map<String, Object>> res = null;
        try {
            PageInfo pageInfo = new PageInfo();
            pageInfo.setCurPage(page);
            List<Board> boards = boardService.pageBoard(pageInfo);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("pageInfo", pageInfo);
            map.put("boards", boards);
            res = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

    @PutMapping("/api/delete/{id}")
    public ResponseEntity<Integer> deleteBoard(@PathVariable Long id,
                                              @RequestParam("password") String password) {
        ResponseEntity<Integer> res = null;
        try {
            Integer msgNo = boardService.deleteBoard(id, password);
            res = new ResponseEntity<Integer>(msgNo, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            res = new ResponseEntity<Integer>(HttpStatus.BAD_REQUEST);
        }
        return res;
    }

}
