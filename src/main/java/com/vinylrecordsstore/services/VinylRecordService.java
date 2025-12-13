package com.vinylrecordsstore.services;

import com.vinylrecordsstore.dto.VinylDTO;
import com.vinylrecordsstore.entities.VinylRecord;
import com.vinylrecordsstore.enums.Genre;
import com.vinylrecordsstore.exceptions.NotFoundException;
import com.vinylrecordsstore.repositories.VinylRecordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class VinylRecordService {

    private final VinylRecordRepository vinylRecordRepository;
    private final ModelMapper modelMapper;

    @Cacheable(value = "catalogSearch")
    public Page<VinylRecord> searchVinyls(String title,
                                          String artist,
                                          Genre genre,
                                          BigDecimal minPrice,
                                          BigDecimal maxPrice,
                                          int page) {

        PageRequest pageable = PageRequest.of(
                page,
                10,
                Sort.by(Sort.Direction.DESC, "lastSupplyDate")
        );
        String normalizedTitle = (title == null || title.isBlank()) ? "" : title;
        String normalizedArtist = (artist == null || artist.isBlank()) ? "" : artist;
        return vinylRecordRepository.searchVinyls(normalizedTitle, normalizedArtist, genre, minPrice, maxPrice, pageable);
    }

    @Transactional(readOnly = true)
    public Page<VinylRecord> getAllVinyls(int page) {
        return vinylRecordRepository.findAll(
                PageRequest.of(
                        page,
                        10,
                        Sort.by(Sort.Direction.DESC, "lastSupplyDate") // Сначала свежие поставки
                )
        );
    }

    @Transactional(readOnly = true)
    public VinylRecord getById(Long id) {
        return vinylRecordRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vinyl not found: " + id));
    }

    @Transactional
    @CacheEvict(cacheNames = {"catalogSearch", "topVinyls"}, allEntries = true)
    public void addVinyl(VinylDTO vinylDTO) {
        VinylRecord vinyl = modelMapper.map(vinylDTO, VinylRecord.class);
        vinyl.setLastSupplyDate(LocalDateTime.now());
        vinylRecordRepository.save(vinyl);
        log.info("Created vinyl {}", vinyl.getTitle());
    }

    @Transactional
    @CacheEvict(cacheNames = {"catalogSearch", "topVinyls"}, allEntries = true)
    public void updateVinyl(Long id, VinylDTO vinylDTO) {
        VinylRecord existing = getById(id);
        int oldQuantity = existing.getQuantity();

        existing.setTitle(vinylDTO.getTitle());
        existing.setArtist(vinylDTO.getArtist());
        existing.setGenre(vinylDTO.getGenre());
        existing.setReleaseDate(vinylDTO.getReleaseDate());
        existing.setDescription(vinylDTO.getDescription());
        existing.setPrice(vinylDTO.getPrice());
        existing.setQuantity(vinylDTO.getQuantity());
        existing.setPhoto(vinylDTO.getPhoto());

        if (existing.getQuantity() != oldQuantity) {
            existing.setLastSupplyDate(LocalDateTime.now());
        }

        vinylRecordRepository.save(existing);
        log.info("Updated vinyl {}", existing.getTitle());
    }

    @Transactional
    @CacheEvict(value = {"catalogSearch", "topVinyls"}, allEntries = true)
    public void deleteById(Long id) {
        vinylRecordRepository.deleteById(id);
    }
}