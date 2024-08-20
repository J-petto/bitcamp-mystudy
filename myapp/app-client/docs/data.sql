-- 회원 데이터
insert into myapp_users(user_id, name, email, pwd) values
  (1, 'admin', 'admin@test.com', sha1('0000')),
  (11, 'user1', 'user1@test.com', sha1('1111')),
  (12, 'user2', 'user2@test.com', sha1('2222')),
  (13, 'user3', 'user3@test.com', sha1('3333')),
  (14, 'user4', 'user4@test.com', sha1('4444')),
  (15, 'user5', 'user5@test.com', sha1('5555')),
  (16, 'user6', 'user6@test.com', sha1('6666')),
  (17, 'user7', 'user7@test.com', sha1('7777')),
  (18, 'user8', 'user8@test.com', sha1('8888')),
  (19, 'user9', 'user9@test.com', sha1('9999')),
  (20, 'user10', 'user10@test.com', sha1('1010'));

-- 게시글 데이터
insert into myapp_boards(board_id, title, content, user_id) values
  (1, '제목1', '내용', 11),
  (2, '제목2', '내용', 11),
  (3, '제목3', '내용', 13),
  (4, '제목4', '내용', 16),
  (5, '제목5', '내용', 19),
  (6, '제목6', '내용', 20),
  (7, '제목7', '내용', 20);

-- 프로젝트 데이터
insert into myapp_projects(project_id, title, description, start_date, end_date) values
  (101, '프로젝트1', '설명', '2024-1-1', '2024-2-15'),
  (102, '프로젝트2', '설명', '2024-2-1', '2024-3-15'),
  (103, '프로젝트3', '설명', '2024-3-1', '2024-4-15');

-- 프로젝트 멤버
insert into myapp_project_members(project_id, user_id) values
  (101, 11), (101, 12), (101, 15),
  (102, 15), (102, 16), (102, 19),
  (103, 14), (103, 17), (103, 19);
