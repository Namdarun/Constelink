export interface RecoveryDiaryData {
  diaryId: number
  beneficiaryId : number,
  beneficiaryName: string,
  beneficiaryDisease: string,
  beneficiaryPhoto: string,
  beneficiaryAmountGoal: number,
  beneficiaryAmountRaised : number,
  beneficiaryBirthday : number,
  hospitalName: string,
}

export interface RecoveryDiaryDetailData {
  // beneficiaryInfo
  beneficiaryId : number,
  beneficiaryName: string,
  beneficiaryBirthday : number,
  beneficiaryDisease: string,
  beneficiaryPhoto: string,
  beneficiaryAmountRaised : number,
  beneficiaryAmountGoal: number,
  hospitalName: string,
}  
  // beneficiaryDiaries

export interface RecoveryDiaries {
  beneficiaryName: string,
  diaryId: number,
  beneficiaryId : number | string,
  diaryRegisterDate: number | string,  
  diaryPhoto: string,
  diaryTitle: string,
  diaryContent: string,
  diaryAmountSpent: number,
}

export interface RecoveryDiaryCreate {
  beneficiaryId? : number | string;
  diaryPhoto: string,
  diaryTitle: string | number,
  diaryContent: string,
  diaryAmountSpent: number,
}

