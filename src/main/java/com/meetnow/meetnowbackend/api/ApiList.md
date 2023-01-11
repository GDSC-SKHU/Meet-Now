### 0. 유저 생성  
- #### [POST] /signup
- 요청 - 사용자는 이름 입력
  - { "username" : "주동석" }
  
- 응답 - JWT 토큰 반환

### 1. 사용자가 속한 방의 리스트 조회 
- #### [GET] /rooms
- 요청 - 사용자는 BEARER 토큰을 Authorization 헤더에 담아 요청
- 응답 - 해당 토큰에 맞는 사용자가 속한 방(의 이름과 초대코드) 전체 반환
  - 응답예시
    {
    "rooms": [
    {
    "roomName": "주동석방",
    "invitationCode": "7883"
    },
    {
    "roomName": "김경민방",
    "invitationCode": "7421"
    }
        ]
    }
### 2. 방 생성  - 완료
- #### [POST] /rooms 
- 요청 - 약속에 대한 정보를 받는다.
- 서버 - 정보를 DB에 저장한다.
- 응답 - (6번째 그림에 보이는) 약속 이름, 초대코드. 
  - 응답예시 :  1번과 같으나, 무조건 단건 정보만 나오는 점이 다름.

- 요청예시
  {
  "roomName" : "주동석방",
  "appointmentHour" : 3,
  "startDate" : "01-10",
  "endDate" : "01-13"
  }

### 3.  시간표 등록 
- #### [POST] /timetables/rooms/{invitationCode}
- 요청 - 어떤 요일, 몇시부터 몇시까지 가능한지 (의 리스트)를 0번에서 발급받은 토큰과 함께 보냄
    - 요청예시
    - [
      {
      "date" : "12.27(금)",
      "appoStart" : "14",
      "appoEnd" : "17"
      },
      {
      "date" : "12.28(토)",
      "appoStart" : "14",
      "appoEnd" : "17"
      },
      {
      "date" : "12.29(일)",
      "appoStart" : "14",
      "appoEnd" : "17"
      }
      ]
    - 위와 같이 배열이나 배열로 변환되는 형식으로 보내야 함.
  
- 응답 - 반환하지 않음. 등록 후 클라이언트에서 4번을 조회해서 시간표 화면으로 돌아가면 될 듯함


### 4. 해당 방의 시간표 모두 조회
- #### [GET] /timetables/rooms/{invitationCode}
- #### 방에 처음 입장할 때, 새로고침 버튼을 누를 때 사용됨
- 요청 - url에 방의 초대코드 보내기
- 응답 - 해당 방의 전체 사용자의 시간표 모두 보내주기
  - 응답예시
  - [
    {
    "username": "주동석",
    "appointmentDateDto": [
    {
    "date": "12.27(금)",
    "appoStart": "14",
    "appoEnd": "17"
    }
    ]
    },
    {
    "username": "조동석",
    "appointmentDateDto": [
    {
    "date": "12.27(금)",
    "appoStart": "14",
    "appoEnd": "17"
    },
    {
    "date": "12.28(토)",
    "appoStart": "14",
    "appoEnd": "17"
    },
    {
    "date": "12.29(일)",
    "appoStart": "14",
    "appoEnd": "17"
    }
    ]
    }
    ]




1. 요청으로 어떤 값들을 받아올지 (요청 DTO)

2. 어떤 작업을 할지

3. 받아서 어떻게 응답할지 (응답 DTO)






