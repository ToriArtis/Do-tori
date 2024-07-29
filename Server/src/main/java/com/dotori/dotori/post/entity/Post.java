package com.dotori.dotori.post.entity;

import com.dotori.dotori.auth.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(value = {AuditingEntityListener.class})
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pid")
    private Long pid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_email", referencedColumnName = "email")
    private User user;

    @Column(name = "nickName")
    private String nickName;

    @Column(nullable = false,length = 500)
    private String content;

    @CreatedDate
    @Builder.Default
    @Column(name = "regDate", updatable = false)
    private LocalDateTime regDate = LocalDateTime.now();

    @LastModifiedDate
    @Column(name = "modDate")
    private LocalDateTime modDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PostThumbnail> thumbnails = new ArrayList<>();

    public void changePost(String content, LocalDateTime modDate, List<PostThumbnail> thumbnails) {
        this.content = content;
        this.modDate = modDate;
        this.thumbnails = thumbnails;
    }

    public void changePost(String content) {
        this.content = content;
    }

    public void addThumbnail(PostThumbnail postThumbnail) {
        this.thumbnails.add(postThumbnail);
        postThumbnail.setPost(this);
    }


}
