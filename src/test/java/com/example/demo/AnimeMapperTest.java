package com.example.demo;

import com.example.demo.entity.Anime;
import com.example.demo.mapper.AnimeMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import static org.assertj.core.api.Assertions.*;

import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;
import java.util.Optional;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
    void 引数のidに対応したアニメがない時_取得されないこと() {
        Optional<Anime> anime = animeMapper.findById(4);
        assertThat(anime).isEmpty();
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
    @ExpectedDataSet("animes.yml")
    void 引数のidに対応したアニメがない時_アニメが更新されないこと() {
       animeMapper.updateAnime(new Anime(5, "Sword Art Online", "Game"));
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
    void 引数のidに対応したアニメがない時＿アニメが削除されないこと() {
        animeMapper.deleteAnime(4);
    }
}
