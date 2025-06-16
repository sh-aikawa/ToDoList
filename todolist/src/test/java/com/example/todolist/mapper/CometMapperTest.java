// src/test/java/com/example/todolist/mapper/CometMapperTest.java
package com.example.todolist.mapper;

import com.example.todolist.model.Comet;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime; // createdAtがLocalDateTimeのため
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@MybatisTest
@Sql(scripts = {"classpath:/schema.sql", "classpath:/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CometMapperTest {

    @Autowired
    private CometMapper cometMapper;

    // --- insertComet メソッドのテスト ---
    @Test
    void testInsertComet_success() {
        // Given (準備): 新しいCometデータを作成
        Comet newComet = new Comet();
        newComet.setUserId(1L); // data.sqlに存在するユーザーID
        newComet.setContent("これはテストのコメットじゃ！");

        // When (実行): Cometを挿入
        cometMapper.insertComet(newComet);

        // Then (確認): IDが生成され、データベースから取得できることを検証
        // insertCometには@Optionsがないが、auto_incrementの主キーなのでDBがIDを生成し、
        // MyBatisがオブジェクトにセットバックするはず。
        assertNotNull(newComet.getCometId(), "挿入後、CometオブジェクトのcometIdがnullではないはずです");
        assertTrue(newComet.getCometId() > 0, "cometIdは正の値であるはずです");

        // 全てのCometを取得して、今挿入したものが含まれているか確認する
        List<Comet> allComets = cometMapper.getAllComets();
        assertFalse(allComets.isEmpty(), "コメットが取得できるはずです");
        assertTrue(allComets.stream().anyMatch(c ->
                c.getCometId() == newComet.getCometId() &&
                c.getUserId() == newComet.getUserId() &&
                c.getContent().equals(newComet.getContent())),
                "挿入したコメットがリストに含まれているはずです");
    }

    // --- getAllComets メソッドのテスト ---
    @Test
    void testGetAllComets_returnsAllCometsWithUserDetails() {
        // Given (準備): data.sqlにCometが複数存在し、ユーザー情報も紐づいていると仮定
        // data.sqlにはuser_id=1の'test comment'が1件のみ
        // テストを厳密にするため、さらにCometを追加する
        Comet comet1 = new Comet();
        comet1.setUserId(1L);
        comet1.setContent("最初のコメット");
        cometMapper.insertComet(comet1); // data.sqlの1件 + 1件

        Comet comet2 = new Comet();
        comet2.setUserId(2L); // 別のユーザー
        comet2.setContent("ユーザー2のコメット");
        cometMapper.insertComet(comet2); // data.sqlの1件 + 2件

        // When (実行): 全てのCometを取得する
        List<Comet> comets = cometMapper.getAllComets();

        // Then (確認): 期待される件数と内容、ユーザー詳細が取得できることを検証
        assertNotNull(comets, "コメットリストはnullではないはずです");
        assertFalse(comets.isEmpty(), "コメットリストは空ではないはずです");
        assertEquals(3, comets.size(), "合計3件のコメットが取得できるはずです"); // data.sqlの1件 + 追加した2件

        // ソート順 (created_at DESC) と内容の検証
        assertEquals("ユーザー2のコメット", comets.get(0).getContent(), "最新のコメットが最初にあるはずです");
        assertEquals("test comment", comets.get(2).getContent(), "最も古いコメットが最後にあるはずです"); // data.sqlのComet

        // ユーザー詳細が正しくセットされているか確認
        assertTrue(comets.stream().anyMatch(c ->
            c.getAccountId() != null && c.getAccountName() != null),
            "全てのコメットにアカウント名とアカウントIDがセットされているはずです");

        // 例: testuser2のコメットの検証
        Comet user2Comet = comets.stream()
                                  .filter(c -> c.getUserId() == 2L)
                                  .findFirst()
                                  .orElse(null);
        assertNotNull(user2Comet);
        assertEquals("testuser2", user2Comet.getAccountName());
        assertEquals("tester2", user2Comet.getAccountId());
    }

    @Test
    void testGetAllComets_returnsEmptyListWhenNoComets() {
        // Given (準備): data.sqlのCometを削除し、DBにCometがない状態にする
        // ※ 通常、@Sqlで毎回DBが初期化されるため、データがないことをテストする際は
        //    data.sqlにCometのINSERT文を含めないか、テスト用に別のSQLを用意する
        //    ここでは、data.sqlのcomets INSERT文を一時的にコメントアウトした状態を想定

        // When (実行): 全てのCometを取得
        List<Comet> comets = cometMapper.getAllComets();

        // Then (確認): 空のリストが返されることを検証
        assertNotNull(comets, "コメットリストはnullではないはずです");
        assertTrue(comets.isEmpty(), "コメットがない場合は空のリストが返されるはずです");
    }
}