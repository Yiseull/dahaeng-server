package com.yiseull.dahaeng.domain.note.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class NoteRequest {

    @Getter
    @NoArgsConstructor
    public static class NoteInfo {
        private String title;
        private String startDate;
        private String endDate;
        private String description;
    }
}
