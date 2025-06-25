package com.petoria.service;

import com.petoria.dto.NoticeDto;
import com.petoria.dto.NoticeResponseDto;
import com.petoria.model.Notice;
import com.petoria.model.NoticeType;
import com.petoria.model.User;
import com.petoria.repository.NoticeRepository;
import com.petoria.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LostAndFoundService {
    private final NoticeRepository noticeRepository;
    private final UserRepository userRepository;

    private final String uploadDir = "uploads/";

    public void createNotice(String username, NoticeDto dto) throws IOException {
        User user = userRepository.findByEmailOrUsername(username, username).orElseThrow();

        List<String> photoPaths = saveFiles(dto.getPhotos());

        Notice n = new Notice();
        n.setTitle(dto.getTitle());
        n.setDescription(dto.getDescription());
        n.setLocation(dto.getLocation());
        n.setType(dto.getType());
        n.setFound(false);
        n.setPhotoPaths(photoPaths);
        n.setUser(user);

        noticeRepository.save(n);
    }

    public Page<NoticeResponseDto> getAllNotices(String currentUsername, int page) {
        User currentUser = userRepository.findByEmailOrUsername(currentUsername, currentUsername).orElse(null);
        Pageable pageable = PageRequest.of(page, 9, Sort.by("id").descending());

        return noticeRepository.findAll(pageable).map(n -> {
            User poster = n.getUser();
            return new NoticeResponseDto(
                    n.getId(),
                    n.getTitle(),
                    n.getDescription(),
                    n.getLocation(),
                    n.getPhotoPaths(),
                    n.isFound(),
                    n.getType(),
                    poster.getUsername(),
                    poster.getProfilePicUrl(),
                    currentUser != null && poster.getId().equals(currentUser.getId())
            );
        });
    }

    public void toggleFoundStatus(Long id, String username) throws AccessDeniedException {
        Notice n = noticeRepository.findById(id).orElseThrow();
        if (!n.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("Not your notice");
        }
        n.setFound(!n.isFound());
        noticeRepository.save(n);
    }

    private List<String> saveFiles(MultipartFile[] files) throws IOException {
        List<String> paths = new ArrayList<>();
        if (files == null) return paths;

        File folder = new File(uploadDir);
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (!created) throw new IOException("Upload folder could not be created");
        }

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            paths.add("/media/uploads/" + fileName);
        }
        return paths;
    }
}
