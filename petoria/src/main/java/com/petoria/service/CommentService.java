package com.petoria.service;

import com.petoria.dto.CommentDto;
import com.petoria.model.BlogPost;
import com.petoria.model.Comment;
import com.petoria.model.Notice;
import com.petoria.model.User;
import com.petoria.repository.BlogPostRepository;
import com.petoria.repository.CommentRepository;
import com.petoria.repository.NoticeRepository;
import com.petoria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepo;
    private final NoticeRepository noticeRepo;
    private final BlogPostRepository blogPostRepo;
    private final UserRepository userRepo;

    public List<CommentDto> getCommentsForNotice(Long noticeId) {
        return commentRepo.findByNoticeIdOrderBySubmissionTimeAsc(noticeId).stream()
                .map(this::toDto)
                .toList();
    }

    public CommentDto addCommentToNotice(Long noticeId, CommentDto dto, User author) {
        Notice notice = noticeRepo.findById(noticeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Notice not found"));

        System.out.println("author: " + author);

        Comment comment = Comment.builder()
                .text(dto.getText())
                .imageUrl(dto.getImageUrl())
                .submissionTime(LocalDateTime.now())
                .author(author)
                .notice(notice)
                .build();

        return toDto(commentRepo.save(comment));
    }

    private CommentDto toDto(Comment c) {
        return CommentDto.builder()
                .id(c.getId())
                .text(c.getText())
                .imageUrl(c.getImageUrl())
                .submissionTime(c.getSubmissionTime())
                .authorUsername(c.getAuthor().getUsername())
                .authorProfilePicUrl(c.getAuthor().getProfilePicUrl())
                .build();
    }

    public List<CommentDto> getCommentsForBlog(Long blogId) {
        List<Comment> comments = commentRepo.findByBlogPostIdOrderBySubmissionTimeAsc(blogId);
        return comments.stream().map(this::toDto).toList();
    }

    public void addCommentToBlog(Long blogId, String username, CommentDto dto) {
        BlogPost blogPost = blogPostRepo.findById(blogId)
                .orElseThrow(() -> new RuntimeException("Blog post not found"));

        User user = userRepo.findByEmailOrUsername(username, username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setImageUrl(dto.getImageUrl());
        comment.setSubmissionTime(LocalDateTime.now());
        comment.setAuthor(user);
        comment.setBlogPost(blogPost);

        commentRepo.save(comment);
    }

}