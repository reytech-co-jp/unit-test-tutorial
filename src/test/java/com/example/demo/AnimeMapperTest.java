package com.example.demo;

import com.example.demo.entity.Anime;
import com.example.demo.mapper.AnimeMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import static org.assertj.core.api.Assertions.*;

import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@DBRider
@SpringBootTest
public class AnimeMapperTest {
    @Autowired
    private AnimeMapper animeMapper;

    @Test
    @DataSet("animes.yml")
    @ExpectedDataSet("expectedAnimes.yml")
    void すべてのアニメが取得できること() {
        List<Anime> animes = animeMapper.findAll();
        assertThat(animes).hasSize(3);
    }

    @Test
    @DataSet("animes.yml")
    void 引数のidに対応したアニメを取得できること() {
        Optional<Anime> anime = animeMapper.findById(1);
        assertThat(anime).contains(new Anime(1, "鬼滅の刃", "Demon"));
    }

    @Test
    @DataSet("animes.yml")
    @ExpectedDataSet(value = "expectedAnimeAfterCreate.yml", ignoreCols = "id")
    void アニメ登録ができること() {
        Anime anime = new Anime("進撃の巨人", "Action");
        animeMapper.createAnime(anime);
    }

    @Test
    @DataSet("animes.yml")
    @ExpectedDataSet("expectedAnimesAfterUpdate.yml")
    void アニメを更新できること() {
        animeMapper.updateAnime(new Anime(1, "Bleach", "Supernatural"));
    }

    @Test
    @DataSet("animes.yml")
    @ExpectedDataSet("expectedAnimesAfterDeleteOne.yml")
    void アニメを削除できること() {
        animeMapper.deleteAnime(1);
    }

    @Test
    @DataSet("animes.yml")
    @ExpectedDataSet("animes.yml")
    void 存在しないアニメのIDでアニメを削除しようとするとき_アニメが削除されないこと() {
        animeMapper.deleteAnime(4);
    }
}
