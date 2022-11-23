package mini.boardapi.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

}
