package org.example.lab4b.Model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Entity
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NonNull
    private int rate;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @NonNull
    private TeacherClass group;

    @Column
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Column
    private String comment;

    @Override
    public String toString() {
        return group.getName();
    }
}
