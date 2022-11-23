package mini.boardapi.domain;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String writer;

    @Column
    private String password;

    @Column
    private String subject;

    @Column
    private String content;

    @Column
    private String filename;

    @Embedded
    private MultipartFile file;

}
