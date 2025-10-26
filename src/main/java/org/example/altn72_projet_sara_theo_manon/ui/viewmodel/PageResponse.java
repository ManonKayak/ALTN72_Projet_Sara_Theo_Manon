package org.example.altn72_projet_sara_theo_manon.ui.viewmodel;

import java.util.List;

public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages
) { }
