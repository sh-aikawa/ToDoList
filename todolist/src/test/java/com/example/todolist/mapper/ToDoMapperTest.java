// src/test/java/com/example/todolist/mapper/ToDoMapperTest.java (前回からの追記部分)
package com.example.todolist.mapper;

import com.example.todolist.model.Task;
// import com.example.todolist.model.Priority; // Priority enumはTaskモデルのimportanceがStringなので不要。もしenumなら必要。
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat; // AssertJを使うなら追加 (任意)

@MybatisTest
@Sql(scripts = {"classpath:/schema.sql", "classpath:/data.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ToDoMapperTest {

    @Autowired
    private ToDoMapper toDoMapper;

   @Test
    void testInsertTask_success() {
        // Given (準備): 新しいタスクのデータを作成する
        // ※ Taskモデルに適切なコンストラクタ（例: IDなし）とGetter/Setterがあることを前提とします。
        //    PriorityはTaskモデルのenumsに合わせて調整してください。
        Task newTask = new Task(
                null,           // taskIdはDBで自動生成されるためnull
                1L,             // ユーザーID (data.sqlに存在するIDを指定)
                "新規タスクのタイトル",
                LocalDate.now().plusDays(7), // 7日後の日付
                "これは新規タスクの詳細説明じゃ。",
                "HIGH"   // 優先度
        );

        // When (実行): ToDoMapperのinsertTaskメソッドを呼び出す
        toDoMapper.insertTask(newTask);

        // Then (確認): ちゃんとタスクがデータベースに保存されたか検証する
        // 1. タスクのIDが自動生成されているはずなので、nullではないことを確認
        assertNotNull(newTask.getTaskId(), "新しいタスクのIDが生成されているはずです");

        // 2. 生成されたIDを使って、データベースからタスクをもう一度取得してみる
        Task foundTask = toDoMapper.getTask(newTask.getTaskId());

        // 3. 取得したタスクの内容が、保存したものと一致するか確認
        assertNotNull(foundTask, "保存したタスクが取得できるはずです");
        assertEquals("新規タスクのタイトル", foundTask.getTitle(), "保存したタイトルと取得したタイトルが一致するはずです");
        assertEquals("これは新規タスクの詳細説明じゃ。", foundTask.getDescription(), "保存した詳細と取得した詳細が一致するはずです");
        assertFalse(foundTask.isChecked(), "デフォルトで未完了のはずです"); // Checkedカラムのデフォルト値に依存
        assertEquals("HIGH", foundTask.getImportance(), "優先度が一致するはずです");
    }

    // --- getTask メソッドのテスト ---
    @Test
    void testGetTask_returnsCorrectTask() {
        // Given (準備): data.sqlに存在するタスクIDを指定
        // ※ data.sqlでID=1のタスクが事前に用意されていると仮定
        long existingTaskId = 1L;

        // When (実行): 指定したIDのタスクを取得する
        Task foundTask = toDoMapper.getTask(existingTaskId);

        // Then (確認): 取得したタスクが期待通りか検証する
        assertNotNull(foundTask, "ID=1のタスクが存在するはずです");
        assertEquals(existingTaskId, foundTask.getTaskId(), "取得したタスクのIDが正しいはずです");
        assertEquals("最初のタスク", foundTask.getTitle(), "data.sqlのタスク1のタイトルと一致するはずです"); // data.sqlの内容に合わせて修正
        assertFalse(foundTask.isChecked(), "タスク1は未完了のはずです"); // data.sqlの内容に合わせて修正
    }

    @Test
    void testGetTask_returnsNullForNonExistingId() {
        // Given (準備): データベースに存在しないタスクIDを指定
        long nonExistingTaskId = 999L;

        // When (実行): 存在しないIDでタスクを取得する
        Task foundTask = toDoMapper.getTask(nonExistingTaskId);

        // Then (確認): nullが返ってくることを検証する
        assertNull(foundTask, "存在しないIDではタスクが取得できないはずです");
    }

    // --- updateChecked メソッドのテスト ---
    @Test
    void testUpdateChecked_setToTrue() {
        // Given (準備): data.sqlに存在する未完了タスクのIDを指定
        long taskIdToComplete = 1L; // タスク1は未完了と仮定

        // When (実行): タスクを完了済みに更新する
        toDoMapper.updateChecked(taskIdToComplete, true);

        // Then (確認): データベースから再度取得し、checkedがtrueになっていることを確認
        Task updatedTask = toDoMapper.getTask(taskIdToComplete);
        assertNotNull(updatedTask, "更新されたタスクが取得できるはずです");
        assertTrue(updatedTask.isChecked(), "タスクが完了済みに更新されているはずです");
    }

    @Test
    void testUpdateChecked_setToFalse() {
        // Given (準備): data.sqlに存在する完了済みタスクのIDを指定
        long taskIdToUncomplete = 2L; // タスク2は完了済みと仮定

        // When (実行): タスクを未完了に更新する
        toDoMapper.updateChecked(taskIdToUncomplete, false);

        // Then (確認): データベースから再度取得し、checkedがfalseになっていることを確認
        Task updatedTask = toDoMapper.getTask(taskIdToUncomplete);
        assertNotNull(updatedTask, "更新されたタスクが取得できるはずです");
        assertFalse(updatedTask.isChecked(), "タスクが未完了に更新されているはずです");
    }

    // --- getTasksByUserId メソッドのテスト ---
    @Test
    void testGetTasksByUserId_returnsCorrectUncheckedTasksForUser() {
        // Given (準備): data.sqlにユーザーID=1に紐づく未完了タスクと完了済みタスクが用意されていると仮定
        //              ユーザーID=2には別のタスクが用意されていると仮定
        long userId = 1L;

        // When (実行): ユーザーID=1の未完了タスクを全て取得する
        List<Task> tasks = toDoMapper.getTasksByUserId(userId);

        // Then (確認): 期待されるタスクの数と内容を検証する
        assertNotNull(tasks, "タスクリストはnullではないはずです");
        assertFalse(tasks.isEmpty(), "タスクリストは空ではないはずです");
        // data.sqlの内容に応じて、正確な件数をアサート
        // 例: ユーザー1に未完了タスクが2件あると仮定
        assertEquals(2, tasks.size(), "ユーザーID=1の未完了タスクが2件あるはずです");

        // 全てのタスクが未完了であり、かつユーザーID=1であることを確認
        assertTrue(tasks.stream().allMatch(t -> !t.isChecked() && t.getUserId() == userId),
                "取得された全てのタスクが未完了かつユーザーID=1のものであるはずです");
    }

    @Test
    void testGetTasksByUserId_returnsEmptyListWhenNoUncheckedTasksForUser() {
        // Given (準備): 未完了タスクがないユーザーID（または存在しないユーザーID）を指定
        // ※ data.sqlでユーザーID=3にはタスクがない、または全て完了済みと仮定
        long userIdWithNoUncheckedTasks = 3L;

        // When (実行): そのユーザーの未完了タスクを取得する
        List<Task> tasks = toDoMapper.getTasksByUserId(userIdWithNoUncheckedTasks);

        // Then (確認): 空のリストが返ってくることを検証する
        assertNotNull(tasks, "タスクリストはnullではないはずです");
        assertTrue(tasks.isEmpty(), "未完了タスクがないユーザーには空のリストが返されるはずです");
    }

    // --- deleteTaskByTaskId メソッドのテスト ---
    @Test
    void testDeleteTaskByTaskId_success() {
        // Given (準備): data.sqlに存在するユーザーIDとタスクIDの組み合わせを指定
        long userId = 1L;
        long taskIdToDelete = 1L; // ユーザー1のタスク1を削除する

        // When (実行): 指定したユーザーとタスクIDでタスクを削除する
        toDoMapper.deleteTaskByTaskId(userId, taskIdToDelete);

        // Then (確認): 削除されたタスクがもう存在しないことを確認する
        assertNull(toDoMapper.getTask(taskIdToDelete), "タスクは削除されているはずです");
    }

    @Test
    void testDeleteTaskByTaskId_noEffectForOtherUserTask() {
        // Given (準備): 存在するタスクだが、ユーザーIDが異なる組み合わせを指定
        long userId = 999L; // 存在しないユーザーID、またはタスクID=1の持ち主ではないユーザー
        long taskId = 1L; // ユーザー1のタスク1

        // When (実行): 他のユーザーのタスクを削除しようとする
        toDoMapper.deleteTaskByTaskId(userId, taskId);

        // Then (確認): タスクが削除されていないことを確認する
        assertNotNull(toDoMapper.getTask(taskId), "他のユーザーのタスクは削除されないはずです");
    }

    // --- editTask メソッドのテスト ---
    @Test
    void testEditTask_updatesAllFields() {
        // Given (準備): data.sqlに存在するタスクID=1のタスクを取得し、値を変更
        Task originalTask = toDoMapper.getTask(1L);
        assertNotNull(originalTask, "既存のタスクが存在するはず");

        originalTask.setTitle("編集されたタイトル");
        originalTask.setLimitDate(LocalDate.now().plusDays(10));
        originalTask.setDescription("編集された詳細説明");
        originalTask.setImportance("LOW");

        // When (実行): タスクを編集する
        toDoMapper.editTask(originalTask);

        // Then (確認): データベースから再度取得し、変更が反映されていることを確認
        Task updatedTask = toDoMapper.getTask(1L);
        assertNotNull(updatedTask, "更新後のタスクが取得できるはず");
        assertEquals("編集されたタイトル", updatedTask.getTitle(), "タイトルが更新されているはずです");
        assertEquals(LocalDate.now().plusDays(10), updatedTask.getLimitDate(), "期日が更新されているはずです");
        assertEquals("編集された詳細説明", updatedTask.getDescription(), "詳細が更新されているはずです");
        assertEquals("LOW", updatedTask.getImportance(), "重要度が更新されているはずです");
    }

    @Test
    void testEditTask_noEffectForNonExistingTask() {
        // Given (準備): 存在しないタスクIDを持つタスクオブジェクトを作成
        Task nonExistingTask = new Task(999L, 1L, "存在しないタスク", LocalDate.now(), "詳細", "MEDIUM", false);

        // When (実行): 存在しないタスクを編集しようとする (MyBatisは更新件数を返すことが多いが、ここではvoidなので効果なしを検証)
        // ※ editTaskがvoidの場合、MyBatisは更新件数を返さないため、直接影響を確認するしかない
        //   もし更新件数を返すようにMapperを変更できるなら、assertEquals(0, updatedRows)と検証できる。
        toDoMapper.editTask(nonExistingTask);

        // Then (確認): 既存のタスクが何も変更されていないことを確認 (もしID=1のタスクがdata.sqlにあるなら)
        Task existingTask = toDoMapper.getTask(1L); // 既存のタスクは変更されていないはず
        assertNotEquals("存在しないタスク", existingTask.getTitle(), "既存のタスクが意図せず変更されてないはずです");
    }


    // --- getAllTasks メソッドのテスト ---
    @Test
    void testGetAllTasks_returnsOnlyUncheckedTasks() {
        // Given (準備): data.sqlに未完了タスクと完了済みタスクが混在していると仮定
        // data.sqlには現在、未完了タスクが2件、完了済みタスクは無し
        // 完了済みタスクを追加してテストケースを強化する
        toDoMapper.insertTask(new Task(null, 1L, "未完了タスクA", LocalDate.now(), "desc", "HIGH"));
        toDoMapper.insertTask(new Task(null, 1L, "完了済みタスクX", LocalDate.now(), "desc", "LOW"));
        // 挿入後、IDがセットされることを利用して、そのタスクを完了済みに更新
        List<Task> initialTasks = toDoMapper.getTasksByUserId(1L); // user1のタスクを取得
        Task taskToComplete = initialTasks.stream().filter(t -> t.getTitle().equals("完了済みタスクX")).findFirst().orElse(null);
        if (taskToComplete != null) {
            toDoMapper.updateChecked(taskToComplete.getTaskId(), true); // 完了済みにする
        }


        // When (実行): 未完了の全タスクを取得する
        List<Task> tasks = toDoMapper.getAllTasks();

        // Then (確認): 完了済みタスクが含まれず、未完了タスクのみが取得できることを検証
        assertNotNull(tasks, "タスクリストはnullではないはずです");
        assertFalse(tasks.isEmpty(), "未完了タスクが取得できるはずです");
        // data.sqlの2件 + 新規挿入の1件 (完了済みタスクXは含まれない)
        // data.sqlのタスクはuser_idが設定されているため、getAllTasks()で取得できない。
        // getAllTasks()はuser_idを指定しないため、現在data.sqlにあるuser_id=1,2のタスクは取得しない。
        // よって、このテストケースのために data.sql に `checked = false` で user_id を含まないタスクを追加するか、
        // getAllTasks()のSQLを `SELECT * FROM tasks WHERE checked = false` とする。
        // 現在のSQLは `SELECT * FROM tasks WHERE checked = false` なので、data.sqlにuser_idを含まないタスクは無い。
        // したがって、テストデータとしては、user_idが設定されていなくても取得されるようなタスクを挿入する必要があるか、
        // getAllTasks()のSQLが適切にフィルタリングしているか確認が必要じゃ。
        // DBスキーマにはuser_idはNOT NULLゆえ、user_id=1のタスクを挿入し、その上でchecked=falseのタスクのみが
        // 取得されるかを確認する。
        // 現在のgetAllTasks()はuser_idを考慮しないため、data.sqlのタスクも対象となる。
        
        // data.sql: user1に未完了2件, user2に未完了1件
        // 挿入: user1に未完了タスクA (合計4件未完了)
        // 挿入: user1に完了済みタスクX (チェックボックスでtrueに更新)
        // getAllTasks()は、user_idに関わらず checked=false のタスクを全て取得
        // -> data.sqlのタスク1(user1,未完了), タスク4(user1,未完了), タスク5(user2,未完了)
        // -> 新規挿入のタスクA(user1,未完了)
        // -> 合計4件
        assertEquals(4, tasks.size(), "未完了タスクが4件取得できるはずです");
        assertTrue(tasks.stream().allMatch(task -> !task.isChecked()), "全てのタスクが未完了であるはずです");
    }


    // --- getSelectTasks メソッドのテスト ---
    @Test
    void testGetSelectTasks_returnsTasksForSpecificDateOrderedByImportance() {
        // Given (準備): 特定の期日のタスクと、そうでないタスクを用意。重要度も異なるものを混ぜる。
        // data.sqlを基に、以下のタスクを想定してテストデータを用意
        // Task(ID, userId, title, limitDate, description, importance, checked)
        // ユーザー1のタスク
        toDoMapper.insertTask(new Task(null, 1L, "本日高優先度", LocalDate.now(), "詳細", "HIGH", false));
        toDoMapper.insertTask(new Task(null, 1L, "本日低優先度", LocalDate.now(), "詳細", "LOW", false));
        toDoMapper.insertTask(new Task(null, 1L, "明日高優先度", LocalDate.now().plusDays(1), "詳細", "HIGH", false));
        // data.sqlにあるタスクID=1のタスクも対象となる: '最初のタスク', '2025-06-20', 'HIGH'
        // data.sqlにあるタスクID=4のタスクも対象となる: '期日古い未完了タスク', '2025-06-10', 'HIGH'

        LocalDate targetDate = LocalDate.now();

        // When (実行): 特定の期日のタスクを取得
        List<Task> tasks = toDoMapper.getSelectTasks(targetDate);

        // Then (確認): 
        assertNotNull(tasks, "タスクリストはnullではないはずです");
        assertFalse(tasks.isEmpty(), "タスクが取得できるはずです");
        
        // 期待されるソート順 (importance ASC)
        // HIGH < LOW のような順序を想定 (文字列比較になるため注意)
        // SQLは ORDER BY importance ASC なので、アルファベット順
        // "HIGH", "LOW", "MEDIUM" => "HIGH", "LOW", "MEDIUM" の順になる
        // もしEnumでHIGH, MEDIUM, LOWが定義されていれば、その順序でソート可能
        // 現在のSQLだと importance TEXT なので文字列としてソートされる
        // "HIGH" < "LOW" < "MEDIUM"
        assertEquals(2, tasks.size(), "本日日付の未完了タスクが2件あるはずです"); // "本日高優先度", "本日低優先度"

        // 重要度で昇順ソートされていることを確認 ("HIGH"が"LOW"より小さいという文字列比較に基づき)
        assertEquals("HIGH", tasks.get(0).getImportance(), "最初が高優先度であるはずです");
        assertEquals("LOW", tasks.get(1).getImportance(), "次が低優先度であるはずです");

        // フィルタリングが正しいことを確認
        assertTrue(tasks.stream().allMatch(t -> t.getLimitDate().equals(targetDate) && !t.isChecked()),
                "取得された全てのタスクが指定期日で未完了であるはずです");
    }

    @Test
    void testGetSelectTasks_returnsEmptyListWhenNoTasksForDate() {
        // Given (準備): タスクがない遠い未来の日付
        LocalDate futureDate = LocalDate.now().plusYears(10);

        // When (実行):
        List<Task> tasks = toDoMapper.getSelectTasks(futureDate);

        // Then (確認): 空のリストが返されること
        assertNotNull(tasks, "タスクリストはnullではないはずです");
        assertTrue(tasks.isEmpty(), "指定期日のタスクがない場合は空のリストが返されるはずです");
    }

    // --- deleteTask メソッドのテスト ---
    @Test
    void testDeleteTask_deletesOnlyCheckedTasksForUser() {
        // Given (準備): ユーザーID=1に完了済みタスクと未完了タスクが混在するようdata.sqlを調整
        // data.sqlにタスクID=2の完了済みタスクと、タスクID=1の未完了タスクがある
        // さらに完了済みタスクを追加してテスト
        toDoMapper.insertTask(new Task(null, 1L, "ユーザー1の完了済みタスクX", LocalDate.now(), "詳細", "HIGH", false));
        List<Task> user1Tasks = toDoMapper.getTasksByUserId(1L);
        // 新規挿入されたタスクIDを取得して完了済みフラグを立てる
        Task taskToMarkComplete = user1Tasks.stream().filter(t -> t.getTitle().equals("ユーザー1の完了済みタスクX")).findFirst().orElse(null);
        if (taskToMarkComplete != null) {
            toDoMapper.updateChecked(taskToMarkComplete.getTaskId(), true);
        }
        
        long userId = 1L;
        
        // When (実行): ユーザーID=1の完了済みタスクを全て削除
        toDoMapper.deleteTask(userId);

        // Then (確認): ユーザーID=1の未完了タスクが残っており、完了済みタスクが削除されていることを確認
        List<Task> remainingTasks = toDoMapper.getTasksByUserId(userId);
        assertNotNull(remainingTasks, "残ったタスクリストはnullではないはずです");
        assertFalse(remainingTasks.isEmpty(), "未完了タスクは残っているはずです");
        // data.sqlのタスク1 (未完了), タスク3 (未完了), タスク4 (未完了) は残るはず
        assertEquals(3, remainingTasks.size(), "ユーザー1の未完了タスクのみが残るはずです");
        assertTrue(remainingTasks.stream().allMatch(t -> !t.isChecked()), "残ったタスクは全て未完了であるはずです");

        // 完了済みタスクだったIDが取得できないことを確認（例: タスク2や新規追加の完了済みタスク）
        assertNull(toDoMapper.getTask(2L), "タスクID=2（完了済み）は削除されているはずです");
        if (taskToMarkComplete != null) {
             assertNull(toDoMapper.getTask(taskToMarkComplete.getTaskId()), "新規追加の完了済みタスクも削除されているはずです");
        }
    }

    @Test
    void testDeleteTask_noEffectWhenNoCheckedTasksForUser() {
        // Given (準備): ユーザーID=1には未完了タスクのみがある状態 (data.sqlのままで良い)
        long userId = 1L;
        
        // When (実行): ユーザーID=1の完了済みタスクを削除 (実際にはない)
        toDoMapper.deleteTask(userId);

        // Then (確認): 未完了タスクが削除されていないことを確認
        List<Task> tasks = toDoMapper.getTasksByUserId(userId);
        assertNotNull(tasks, "タスクリストはnullではないはずです");
        assertFalse(tasks.isEmpty(), "未完了タスクは残っているはずです");
        assertEquals(3, tasks.size(), "未完了タスクの数が変わらないはずです"); // data.sqlのタスク1,3,4が残る
    }

    // --- getFinishTasks メソッドのテスト ---
    @Test
    void testGetFinishTasks_returnsOnlyCheckedTasks() {
        // Given (準備): 完了済みタスクが複数ある状態にする
        // data.sqlのタスク2(user_id=1)は完了済み
        toDoMapper.insertTask(new Task(null, 1L, "新規完了済みタスクY", LocalDate.now(), "詳細", "MEDIUM", false));
        List<Task> user1Tasks = toDoMapper.getTasksByUserId(1L);
        Task taskY = user1Tasks.stream().filter(t -> t.getTitle().equals("新規完了済みタスクY")).findFirst().orElse(null);
        if (taskY != null) {
            toDoMapper.updateChecked(taskY.getTaskId(), true);
        }

        // When (実行): 完了済みタスクを全て取得
        List<Task> finishTasks = toDoMapper.getFinishTasks();

        // Then (確認): 完了済みタスクのみが取得できること、未完了タスクは含まれないこと
        assertNotNull(finishTasks, "完了済みタスクリストはnullではないはずです");
        assertFalse(finishTasks.isEmpty(), "完了済みタスクが取得できるはずです");
        assertEquals(2, finishTasks.size(), "完了済みタスクが2件あるはずです"); // data.sqlのタスク2 + 新規追加のタスクY
        assertTrue(finishTasks.stream().allMatch(Task::isChecked), "取得された全てのタスクが完了済みであるはずです");
        assertFalse(finishTasks.stream().anyMatch(t -> t.getTitle().equals("最初のタスク")), "未完了タスクは含まれないはずです");
    }

    @Test
    void testGetFinishTasks_returnsEmptyListWhenNoCheckedTasks() {
        // Given (準備): 全てのタスクを未完了にする（またはdata.sqlから完了済みタスクを削除）
        // data.sqlのタスク2を未完了に戻す（一時的にテストのために）
        toDoMapper.updateChecked(2L, false);

        // When (実行): 完了済みタスクを取得
        List<Task> finishTasks = toDoMapper.getFinishTasks();

        // Then (確認): 空のリストが返されること
        assertNotNull(finishTasks, "完了済みタスクリストはnullではないはずです");
        assertTrue(finishTasks.isEmpty(), "完了済みタスクがない場合は空のリストが返されるはずです");
    }

    // --- getSortDescTasks メソッドのテスト ---
    @Test
    void testGetSortDescTasks_sortsByLimitDateDescending() {
        // Given (準備): ユーザーID=1の未完了タスクで、異なる期日のものを用意 (data.sqlの内容も考慮)
        // data.sql:
        // (1, 1, '最初のタスク', '2025-06-20', ..., FALSE),
        // (3, 1, '二番目の未完了タスク', '2025-06-25', ..., FALSE),
        // (4, 1, '期日古い未完了タスク', '2025-06-10', ..., FALSE);

        long userId = 1L;

        // When (実行): ユーザーID=1の未完了タスクを期日の降順で取得
        List<Task> tasks = toDoMapper.getSortDescTasks(userId);

        // Then (確認): 
        assertNotNull(tasks, "タスクリストはnullではないはずです");
        assertFalse(tasks.isEmpty(), "タスクが取得できるはずです");
        assertEquals(3, tasks.size(), "ユーザー1の未完了タスクが3件あるはずです"); // data.sqlの3件

        // 期日が新しい順 (降順) になっていることを確認
        // 2025-06-25 (タスク3), 2025-06-20 (タスク1), 2025-06-10 (タスク4)
        assertThat(tasks)
            .extracting(Task::getLimitDate)
            .containsExactly(
                LocalDate.of(2025, 6, 25), // 二番目の未完了タスク
                LocalDate.of(2025, 6, 20), // 最初のタスク
                LocalDate.of(2025, 6, 10)  // 期日古い未完了タスク
            );
        assertTrue(tasks.stream().allMatch(t -> !t.isChecked()), "全てのタスクが未完了であるはずです");
    }

    // --- getSortAscTasks メソッドのテスト ---
    @Test
    void testGetSortAscTasks_sortsByLimitDateAscending() {
        // Given (準備): 上記testGetSortDescTasksと同じデータを使用
        long userId = 1L;

        // When (実行): ユーザーID=1の未完了タスクを期日の昇順で取得
        List<Task> tasks = toDoMapper.getSortAscTasks(userId);

        // Then (確認):
        assertNotNull(tasks, "タスクリストはnullではないはずです");
        assertFalse(tasks.isEmpty(), "タスクが取得できるはずです");
        assertEquals(3, tasks.size(), "ユーザー1の未完了タスクが3件あるはずです"); // data.sqlの3件

        // 期日が古い順 (昇順) になっていることを確認
        // 2025-06-10 (タスク4), 2025-06-20 (タスク1), 2025-06-25 (タスク3)
        assertThat(tasks)
            .extracting(Task::getLimitDate)
            .containsExactly(
                LocalDate.of(2025, 6, 10), // 期日古い未完了タスク
                LocalDate.of(2025, 6, 20), // 最初のタスク
                LocalDate.of(2025, 6, 25)  // 二番目の未完了タスク
            );
        assertTrue(tasks.stream().allMatch(t -> !t.isChecked()), "全てのタスクが未完了であるはずです");
    }

    // --- getTasksforRoullete メソッドのテスト ---
    @Test
    void testGetTasksforRoullete_returnsOnlyUncheckedTasksForUser() {
        // Given (準備): ユーザーID=1の未完了タスクと完了済みタスクが混在するようdata.sqlを調整
        long userId = 1L;

        // When (実行): ルーレット用のタスクを取得 (未完了かつユーザーID指定)
        List<Task> tasks = toDoMapper.getTasksforRoullete(userId);

        // Then (確認): ユーザーID=1の未完了タスクのみが取得できることを検証
        assertNotNull(tasks, "タスクリストはnullではないはずです");
        assertFalse(tasks.isEmpty(), "タスクが取得できるはずです");
        assertEquals(3, tasks.size(), "ユーザーID=1の未完了タスクが3件あるはずです"); // data.sqlのタスク1,3,4
        assertTrue(tasks.stream().allMatch(t -> !t.isChecked() && t.getUserId() == userId),
                "取得された全てのタスクが未完了かつユーザーID=1のものであるはずです");
    }

    @Test
    void testGetTasksforRoullete_returnsEmptyListWhenNoUncheckedTasksForUser() {
        // Given (準備): 未完了タスクがないユーザーID（または存在しないユーザーID）
        long userIdWithNoUncheckedTasks = 3L; // data.sqlでこのユーザーに未完了タスクがないと仮定

        // When (実行):
        List<Task> tasks = toDoMapper.getTasksforRoullete(userIdWithNoUncheckedTasks);

        // Then (確認): 空のリストが返されること
        assertNotNull(tasks, "タスクリストはnullではないはずです");
        assertTrue(tasks.isEmpty(), "未完了タスクがない場合は空のリストが返されるはずです");
    }
}