package com.example.demo.mapper;

import com.example.demo.entity.Anime;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AnimeMapperTest {
    @Autowired
    AnimeMapper animeMapper;

    @Test
    @DataSet(value = "anime.yml")
    void アニメを全件取得できること() {
        List<Anime> animeList = animeMapper.findAll();
        List<Anime> expectedAnimeList = Arrays.asList(
            new Anime(1, "鬼滅の刃", "ダークファンタジー"),
            new Anime(2, "SPY×FAMILY", "ホームコメディ")
        );
        assertThat(animeList).hasSize(2).isEqualTo(expectedAnimeList);
    }

    @Test
    @DataSet(value = "anime.yml")
    void 引数のidに対応したアニメを取得できること() {
        Optional<Anime> anime = animeMapper.findById(1);
        assertThat(anime).contains(new Anime(1, "鬼滅の刃", "ダークファンタジー"));
    }

    @Test
    @DataSet(value = "anime.yml")
    void 引数のidに対応したアニメがない時_空のOptionalを取得すること() {
        Optional<Anime> anime = animeMapper.findById(7);
        assertThat(anime).isEqualTo(Optional.empty());
    }

    @Test
    @DataSet(value = "empty.yml")
    @ExpectedDataSet(value = "insert/expectedAfterInsertAnimes.yml", ignoreCols = "id")
    void アニメを登録できること() {
        animeMapper.createAnime(new Anime(3, "ドクターストーン", "ファンタジー"));
    }

    @Test
    @DataSet(value = "anime.yml")
    @ExpectedDataSet(value = "delete/expectedAfterDeleteAnimes.yml")
    void 引数のidに対応したアニメを削除できること() {
        animeMapper.deleteAnime(1);
    }

    @Test
    @DataSet(value = "anime.yml")
    @ExpectedDataSet(value = "anime.yml")
    void 引数のidに対応したアニメがない時_アニメが削除されないこと() {
        animeMapper.deleteAnime(7);
    }

    @Test
    @DataSet(value = "anime.yml")
    @ExpectedDataSet(value = "update/expectedAfterUpdateAnimes.yml")
    void アニメを更新できること() {
        animeMapper.updateAnime(new Anime(1, "Demon Slayer", "ダークファンタジー"));
    }

    @Test
    @DataSet(value = "anime.yml")
    @ExpectedDataSet(value = "anime.yml")
    void 引数のidに対応したアニメがない時_アニメが更新されないこと() {
        animeMapper.updateAnime(new Anime(5, "Demon Slayer", "ダークファンタジー"));
    }
}
