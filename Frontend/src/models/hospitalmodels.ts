export interface HosBenInfo {
  beneficiaryId: number,
  beneficiaryName: string,
  beneficiaryPhoto: string,
  beneficiaryBirthday: number,
  beneficiaryDisease: string,
  beneficiaryAmountGoal: number,
  beneficiaryAmountRaised: number,
  beneficiaryStatus: string,

  hospitalId: number,
  hospitalLink: string,
  hospitalName: string
}

export interface HosFundraisingData {
  fundraisingId : number,
  beneficiaryId : number,
  categoryName : string,
  fundraisingAmountRaised : number,
  fundraisingAmountGoal : number,
  fundraisingStartTime : number,
  fundraisingEndTime : number,
  fundraisingTitle : string,
  fundraisingStory : string,
  fundraisingThumbnail : string,
  fundraisingPeople : number,
  fundraisingIsDone : boolean,
  fundraisingBookmarked : boolean,
  hospitalName : string,
  beneficiaryName : string,
  beneficiaryDisease : string,
  beneficiaryStatus : string,
  beneficiaryBirthday : number,
  beneficiaryPhoto : string
}