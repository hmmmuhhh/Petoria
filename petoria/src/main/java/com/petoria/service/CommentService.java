package com.petoria.service;

import com.petoria.dto.CommentDto;
import com.petoria.model.Comment;
import com.petoria.model.LostAndFoundNotice;
import com.petoria.model.User;
import com.petoria.repository.CommentRepository;
import com.petoria.repository.LostAndFoundNoticeRepository;
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
    private final LostAndFoundNoticeRepository noticeRepo;

    public List<CommentDto> getCommentsForNotice(Long noticeId) {
        return commentRepo.findByNoticeIdOrderBySubmissionTimeAsc(noticeId).stream()
                .map(this::toDto)
                .toList();
    }

    public CommentDto addComment(Long noticeId, CommentDto dto, User author) {
        LostAndFoundNotice notice = noticeRepo.findById(noticeId)
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
}