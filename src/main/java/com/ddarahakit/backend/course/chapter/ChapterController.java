package com.ddarahakit.backend.course.chapter;

import com.ddarahakit.backend.course.chapter.model.PostChapterReq;
import com.ddarahakit.backend.course.chapter.model.PostChapterRes;
import com.ddarahakit.backend.config.BaseException;
import com.ddarahakit.backend.config.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    ChapterService chapterService;

    @ResponseBody
    @PostMapping("/create")
    public BaseResponse<PostChapterRes> createChapter(@RequestBody PostChapterReq postChapterReq) {
        try {
            PostChapterRes postChapterRes = chapterService.createChapter(postChapterReq);
            return new BaseResponse<>(postChapterRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}


