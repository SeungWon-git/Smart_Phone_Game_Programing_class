# 🔪 Slash It! (후르츠 닌자 모작)

## 🎮 개요
 - 게임 장르: 스와이프 액션 캐주얼 게임
 - 플랫폼: Android
 - 플레이 인원: 1인
 - 1인 개발
 - **게임의 목표**: 화면에서 날라다니는 다양한 슬라임들을 터치&드래그를 통해 '*슬라이스*'하여, 제한시간이 다 지나가기 전에 목표점수를 채워나가 스테이지들을 클리어 해나가는 게임!
 - **구현 내용**:
   > + **Android Studio를 활용**하여 2D 모바일 게임 개발
   > + **총 4개의 스테이지**(+ 스테이지 별 새로운 슬라임 등장)
   > + **랜덤하게 생성**되는 슬라임들 (크기, 색상, 모양, 종류, 출현 위치, 속도)
   > + 슬라임을 **연속해서 여러번 절단이 가능**하게 구현
   > + **사운드**(배경음, 효과음), 다시시작 기능
 - 개발에 사용된 기술 스택:
   + Android Studio, Java
 - 시연 영상: [▶️ 유투브 영상 보기](https://www.youtube.com/)
<img width="323" height="700" alt="image" src="https://github.com/user-attachments/assets/c156ceec-3386-43a8-9486-012cf0c2441a" />


---

## 📝 구현 내용 상세 설명
   
### 🎨 Custom View 컴포넌트 활용
  - Custom View 컴포넌트를 만들어 게임 내 여러가지 UI에 커스텀 이미지, 애니메이션을 적용
  * 관련 코드: [스코어 애니메이션](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/blob/606864cee5f3b1ed2a79c8dee249ed5706acb208/app/src/main/java/kr/ac/tukorea/ge/jsw01/framework/objects/Score.java#L48), [남은 시간 게이지 애니메이션](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/blob/main/app/src/main/java/kr/ac/tukorea/ge/jsw01/framework/util/Gauge.java), [스테이지 표시 애니메이션](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/blob/main/app/src/main/java/kr/ac/tukorea/ge/jsw01/s2016180039/slashit/scenes/StageDisplay.java)
    
### 📦 Framework Class 정의
  - Scene, Interfaces, GameObjects와 같은 다양한 **프레임워크 객체를 미리 정의**하고, 이를 상속받아 활용함으로써 객체의 효율적인 생성과 관리를 구현
  * 관련 코드: [프레임워크 폴더](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/tree/main/app/src/main/java/kr/ac/tukorea/ge/jsw01/framework)
    
### ♻️ Recyclable 객체 활용
  - Recyclable 객체를 이용하여 객체의 **재활용**을 통해 ***메모리 사용 효율 극대화***
  * 관련 코드: [슬라임 재활용 코드](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/blob/606864cee5f3b1ed2a79c8dee249ed5706acb208/app/src/main/java/kr/ac/tukorea/ge/jsw01/s2016180039/slashit/scenes/Slime.java#L94), [재활용 구조](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/blob/606864cee5f3b1ed2a79c8dee249ed5706acb208/app/src/main/java/kr/ac/tukorea/ge/jsw01/framework/game/RecycleBin.java#L13)

### 🟢👁️ 슬라임
  - 슬라임은 랜덤하게 생성되는데, 이는 날라오는 방향, 날라오는 위치, 슬라임의 크기&속도, 슬라임의 모양&색상, 슬라임의 눈의 색상이 랜덤하다.
  - 슬라임의 속도는 해당 슬라임의 크기에 따라 설정된다. (클 수록 느리고, 작을 수록 빠름)
  - 슬라임은 연속으로 자르는 횟수에 따라 획득 점수가 높으며, 연속으로 많이 잘라서 슬라임의 크기가 일정 이하가 되면 최대점수를 획득하고 슬라임은 결국 소멸된다.
    * 슬라임은 생성 될 때마다 랜덤한 효과음을 내며, 잘릴 때는 작은 물방울 파티클과 함께 2개의 더 작은 슬라임을 생성하고, 너무 크기가 작게 잘려 완전히 소멸할 때는 특별한 소리를 낸다.
  - 슬라임의 종류는 총 3가지로 '일반 슬라임, 감점 슬라임, 가속 슬라임' 이 존재한다.
  - 
    <img width="180" height="162" alt="image" src="https://github.com/user-attachments/assets/f1fe4b4a-f10c-4f58-8682-de2adaa2c53b" />
    <img width="172" height="166" alt="image" src="https://github.com/user-attachments/assets/20faebbd-c99c-4dd7-8b2a-3eea00f3d797" />
    <img width="141" height="143" alt="image" src="https://github.com/user-attachments/assets/e3dfd19d-806f-4339-94ec-59cb6e7183a6" />

  - 스테이지 진행 될 수록 크기가 더 작고 빠른 슬라임들이 더 자주 생성되며, 특수 슬라임 또한 더 많이 나온다.    
  * 관련 코드: [슬라임 랜덤 생성](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/blob/5f8ebe96361c96374d1f56729029fd6646074c82/app/src/main/java/kr/ac/tukorea/ge/jsw01/s2016180039/slashit/scenes/Slime.java#L51)

### 📚 GameObject Layering을 활용
  - GameObject Layering을 이용하여 게임 오브젝트들의 그리는 순서 정의하고 객체들을 분류, 구조화한다.
  * 관련 코드: [GameObject Layering 적용](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/blob/606864cee5f3b1ed2a79c8dee249ed5706acb208/app/src/main/java/kr/ac/tukorea/ge/jsw01/framework/game/Scene.java#L125), [GameObject Layering 설정](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/blob/606864cee5f3b1ed2a79c8dee249ed5706acb208/app/src/main/java/kr/ac/tukorea/ge/jsw01/s2016180039/slashit/scenes/MainScene.java#L29)

### 👉📲 Touch Event Handling 활용
  - Touch event handling을 이용하여 터치 입력에대한 처리 수행 (→ 슬라임 자르기)
  * 관련 코드: [슬라임 자르기](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/blob/606864cee5f3b1ed2a79c8dee249ed5706acb208/app/src/main/java/kr/ac/tukorea/ge/jsw01/s2016180039/slashit/scenes/MainScene.java#L153), [슬라임 슬라이스 실제 반응](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/blob/606864cee5f3b1ed2a79c8dee249ed5706acb208/app/src/main/java/kr/ac/tukorea/ge/jsw01/s2016180039/slashit/scenes/Slime.java#L202)
    
### 🗂️ 리소스 관리
  - SoundPool, BitMapPool을 이용하여 리소스 관리
  * 관련 코드: [SoundPool class](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/blob/606864cee5f3b1ed2a79c8dee249ed5706acb208/app/src/main/java/kr/ac/tukorea/ge/jsw01/framework/res/Sound.java), [BitMapPool을 class](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/blob/606864cee5f3b1ed2a79c8dee249ed5706acb208/app/src/main/java/kr/ac/tukorea/ge/jsw01/framework/res/BitmapPool.java)
    

---

## 📎 기타
- 소스 코드: 👉 [소스 코드](https://github.com/SeungWon-git/Smart_Phone_Game_Programing_class/tree/main/app/src/main)
