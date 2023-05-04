package com.yiseull.dahaeng.domain.note.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class NoteRequest {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NoteInfo {
        private int noteId;
        private String title;
        private String startDate;
        private String endDate;
        private String description;
    }
}
