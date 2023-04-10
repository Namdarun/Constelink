# ⭐Constelink

<img src="https://user-images.githubusercontent.com/47595515/230431288-f742c280-8831-440d-964c-db73e5d3d2e7.png" width="500"/>

<br/>

### 🎞 기획 의도

- 복지 사각지대에 있어, 치료를 받고싶어도 받지 못하는 환자들을 위한 모금을 개설하기 위함

- 투명한 기부내역 공개를 통해 기부자들이 신뢰를 갖고 기부를 할 수 있게 하기 위함

- 불특정 다수가 아닌 특정 대상에 대한 기부와 해당 환자가 치료받는 일지를 제공해 기부자들이 기부한 보람을 느낄 수 있게

<br/>

### 💡 서비스 특징

- 투명한 기부내역을 통해 신뢰성 있는 기부 가능
- 치료일지를 제공해 기부자들이 보람을 느낄 수 있음

<br/>

### ✔ 주요 기능

- 소셜 회원가입, 로그인
- 병원이 환자를 등록하고, 해당 환자에 대한 모금 개설 가능
- 공지사항을 기능
- 자체 제작 토큰을 사용하여 기부 기록을 블록체인에 투명하게 기록
- 회복일지를 통해 기부 대상자의 치료과정을 확인할 수 있음

<br/>

### 📅 프로젝트 진행 기간

2023.02.20일(월) ~ 2023.04.07(금)

<br/>

## 💛 팀 소개

- **정원철**: Frontend 리더, UI/UX, API 설계 및 관리, 프로젝트 구조 설계, 데이터플로우 관리
- **박정호**: Frontend, UI/UX, Web3.js, 모금
- **남기성**: Frontend, UI/UX, 문서관리, UCC 및 발표, 와이어프레임 설계
- **박윤환**: Backend, DevOps 담당, 아키텍처 설계, 팀장
- **이승헌**: Backend 리더, 스마트 컨트랙트 작성, REST API 개발, Spring Security, 부팀장
- **윤동근**: Backend, REST API 개발, Grpc 통신 개발

<br/>

## ⚙ 개발 환경

🔧 **Backend**

- IntelliJ : 2022.3.1 (Ultimate Edition)
- Open JDK 17
- Spring Boot 3.0.4
- Spring Data JPA
- Spring Security
- OAuth2 Login
- JWT Authentication
- Swagger
- Google Cloud Storage
- MariaDB 10.11.2
- Redis 7.0.10
- gRPC 1.52.1
- Gradle 7.6.1

🔧 **Frontend**

- Visual Studio Code 1.75.1
- Node.js 18.15.0
- react 18.2.0
- redux 1.9.3
- TypeScript 4.9.5

🔧 **Block chain**

- Solidity 0.8.18
- Web3.js 1.9.0

🔧 **CI/CD**

- AWS EC2 Ubuntu 20.04 LTS
- Kubernetes 1.26.2
- CRI-O 1.26.1
- Nginx Ingress Controller 1.6.4
- Jenkins : 2.397
- ArgoCD: 2.6.7+5bcd846

<br/>

## 🗂 프로젝트 폴더 구조

- Frontend

  ```text
  ./src
  ├── assets
  │   ├── fonts
  │   ├── img
  │   └── logo
  ├── components
  │   ├── Modal
  │   ├── cards
  │   ├── footer
  │   ├── header
  │   └── pagination
  ├── models
  ├── pages
  ├── store
  └── web3js
  ```

- Backend - authserver

  ```text
  ./authserver
  ├── common
  │   └── exception
  ├── config
  ├── controller
  ├── dto
  │   └── enums
  ├── filters
  └── jwt
  ```

- Backend - constelinkbeneficiary

  ```text
  ./constelinkbeneficiary
  ├── common
  │   └── exception
  ├── config
  ├── db
  │   ├── controller
  │   ├── dto
  │   │   ├── common
  │   │   ├── enums
  │   │   ├── request
  │   │   └── response
  │   ├── entity
  │   ├── repository
  │   └── service
  ├── grpc
  └── jwt
  ```

- Backend - constelinkfundraising

  ```text
  ./constelinkfundraising
  ├── common
  │   └── exception
  ├── config
  ├── db
  │   ├── controller
  │   ├── dto
  │   │   ├── common
  │   │   ├── enums
  │   │   ├── request
  │   │   └── response
  │   ├── entity
  │   ├── repository
  │   └── service
  └── jwt
  ```

- Backend - constelinkmember

  ```text
  ./constelinkmember
  ├── api
  │   ├── controller
  │   └── service
  ├── common
  │   ├── exception
  │   └── logger
  ├── config
  ├── db
  │   ├── entity
  │   └── repository
  ├── dto
  │   ├── enums
  │   ├── kakao
  │   ├── request
  │   └── response
  ├── grpc
  │   └── service
  ├── security
  │   ├── handler
  │   ├── jwt
  │   ├── principal
  │   ├── repository
  │   └── service
  └── util
  ```

- Backend - constelinknotice

  ```text
  ./constelinknotice
  ├── api
  │   ├── controller
  │   └── service
  ├── common
  │   └── exception
  ├── config
  ├── db
  │   ├── entity
  │   └── repository
  └── dto
  ├── enums
  ├── request
  └── response
  ```

- Backend - constelinkfile

  ```text
  ./constelinkfile
  ├── Exception
  ├── config
  ├── controller
  ├── dto
  ├── service
  └── util
  ```

<br/>

## 🗺 서비스 아키텍처

<img src="https://user-images.githubusercontent.com/96561194/230009649-f013859e-5669-4945-9d44-3cfd98c240d6.png" width="600"/>

<br/>

## 📜 기능 명세서

<details>
    <summary>API</summary>
    <img src="https://user-images.githubusercontent.com/96561194/230003460-6648e5ad-3434-42a7-a79b-be2ca6c04c3f.png" width="600"/>

</details>

<br/>

## 📊 ERD

<img src="https://user-images.githubusercontent.com/96561194/230009227-82e1acf0-87e6-4767-a278-a6945a107d7a.png" width="600"/>

<br/>

### 🤝 컨벤션

- **git 컨벤션**

  ```text
  ### 제목
  # :gitmoji: [FE/BE/공통] 작업내용 (제목과 본문은 한 줄 띄워주세요)
  
  
  ### 본문 - 한 줄에 최대 72 글자까지만 입력하기
  # 무엇을, 왜, 어떻게 했는지
  
  
  # 꼬리말
  # (선택) 이슈번호 작성
  
  #   [커밋 타입]  리스트
  #   :sparkles:          : 기능 (새로운 기능)
  #   :bug:               : 버그 (버그 수정)
  #   :lipstick:          : CSS 등 사용자 UI 디자인 변경
  #   :recycle:           : 리팩토링
  #   :art:               : 스타일 (코드 형식, 세미콜론 추가: 비즈니스 로직에 변경 없음)
  #   :memo:              : 문서 (문서 추가, 수정, 삭제)
  #   :white_check_mark:  : 테스트 (테스트 코드 추가, 수정, 삭제: 비즈니스 로직에 변경 없음)
  #   :hammer:            : 기타 변경사항 (빌드 스크립트 수정 등)
  #   :truck:             : 파일 혹은 폴더명을 수정하거나 옮기는 작업만 하는 경우
  #   :fire:              : 코드, 파일을 삭제하는 작업만 수행한 경우
  #   :twisted_rightwards_arrows:    : 브랜치 합병
  #   :rocket:            : 배포 관련
  # ------------------
  #   [체크리스트]
  #     제목 첫 글자는 대문자로 작성했나요?
  #     제목은 명령문으로 작성했나요?
  #     제목 끝에 마침표(.) 금지
  #     제목과 본문을 한 줄 띄워 분리하기
  #     본문에 여러줄의 메시지를 작성할 땐 "-"로 구분했나요?
  # ------------------
  ```

- **브랜치 전략**

  ```
  🌲 master
      - dev
        - dev-front
          - feature-front/기능명
        - dev-back
          - feature-back/API명
        - fix : 문제가 생긴 브랜치에서 분기
          - fix-front/기능명
          - fix-back/기능명
      - docs/문서타입[ex) README, ppt]
  ```

- **Frontend 코드 컨벤션**

    - 변수명

        - `camelCase`

    - tab

        - `2 spaces`

    - import 순서

        - 라이브러리/모듈 → 커스텀 객체 → 컴포넌트 → css파일

    - 클래스명

        - `kebab-case` 로 작성하기
        - `어떤페이지(컴포넌트)__무슨역할`

- **Backend 코드 컨벤션**

    - 명명법

        - 변수명, 메서드명
            - `camelCase`
        - 의미없는 변수명 사용 지양 → 유지보수의 어려움
        - 메서드 이름은 소문자로 시작, 동사 → ex) getName()
        - 클래스 이름은 대문자로 시작

    - 코딩 스타일 자동적용 설정

        - IntelliJ에 NAVER [캠퍼스 핵데이 Java 코딩 컨벤션](https://naver.github.io/hackday-conventions-java/) 적용

        - 저장시 액션 설정

          <img src="https://user-images.githubusercontent.com/47595515/219520513-12db4f55-b814-4395-9c01-6207abab589f.png" width="600px">

    - 줄바꿈을 CRLF 대신 LF로 변경 (윈도우 한정)

        - 새로 만들어진 파일을 LF로 적용

            <img src="https://user-images.githubusercontent.com/96561194/230012393-1e34f2a1-6ac5-44dd-86c9-344acf268e45.png" width="600"/>

        - 기존 파일을 LF로 변경 방법
            - 현재 파일

                <img src="https://user-images.githubusercontent.com/96561194/230013239-ae5e8162-4275-4ea7-83d2-82850d25239a.png" width="600"/>

            - 디렉토리
                - 바꿀 디렉토리 선택
              
                    <img src="https://user-images.githubusercontent.com/96561194/230014121-1bdc79b6-3679-4718-aa8a-7603fdf262ed.png" width="400"/>
                
                - 파일 - 파일 프로퍼티 - 줄 구분 기호 - LF 선택
          
                    <img src="https://user-images.githubusercontent.com/96561194/230014384-e73de92f-ed4f-4a53-ad06-f8c9130d88d4.png" width="600"/>
<br/>

## 🎨 기능 상세 설명

### 👉 회원가입 , 로그인

- 소셜로그인 (카카오, 구글)

- 소셜로그인 정보를 토대로 회원가입

- 회원정보 수정 가능


<br/><br/><br/>

### 👉 공지사항

- 공지사항을 통해 사이트의 소식을 접할 수 있음


<br/><br/><br/>

### 👉 기부

- 병원측은 등록된 환자들의 모금 등록 가능

- 가부자들은 KakaoPay 간편결제를 통해 자체 제작 토큰과 변환해 기부 가능

- 기부자들은 기부 대상자의 정보를 확인할 수 있음

- 기부한 내역은 블록체인에 기록됨

- 기부 종료 시 해당 병원 지갑으로 기부금 자동 송금

<br/><br/><br/>

### 👉 회복일지

- 모금이 완료된 환자는 병원에서 회복일지 작성 가능
- 환자들이 해당 회복일지를 통해 모금 대상자가 회복되어 가는 과정을 볼 수 있음


<br/><br/><br/>

### 👉 기부내역

- 사용자가 기부한 기부 내역을 확인할 수 있음

<br/><br/><br/>

### 👉 통계

- 현재 사이트에 기부된 총 기부액, 기부 횟수, 수혜자 등을 통계로 나타냄

<br/><br/><br/>

## 📢 Notion

프로젝트 진행 과정에서 필요한 회의, 공지, 일정 등을 원페이지 협업 툴인 노션을 통해 관리했습니다. <br/>
또한 컨벤션 규칙, 브랜치 활용 규칙 등을 노션에 명시해두었고, 팀 미팅에 대한 피드백과 질문을 기록해 두어 언제든 확인할 수 있도록 관리하고 있습니다.

<br/><br/>

## 👨‍👩‍👧 Scrum

매일 아침 10시에 팀 단위로 할 일을 10분 정도 공유했습니다.</br> 유연한 분위기에서 스크럼을 통해서 개발에 집중할 수 있는 팀 분위기를 만들었습니다.
