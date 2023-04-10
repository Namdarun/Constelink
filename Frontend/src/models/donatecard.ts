export interface DonationData {
  beneficiaryDisease: string;
  beneficiaryName: string;
  beneficiaryPhoto: string;
  beneficiaryId: number;
  beneficiaryBirthday: number;

  categoryName: string;
  beneficiaryStatus: string;
  fundraisingAmountGoal: number;
  fundraisingAmountRaised: number;
  fundraisingEndTime: number;
  fundraisingIsDone: boolean;
  fundraisingBookmarked: boolean;
  fundraisingPeople: number;
  fundraisingStartTime: number;
  fundraisingStory: string;
  fundraisingThumbnail: string;
  fundraisingTitle: string;
  fundraisingId: number;
  photo: string;
  hospitalName: string;
  fundraisingWillUse: string;
}

export interface Statistics {
  totalFundraisings: number;
  totalFundraisingsFinished: number;
  totalAmountedCash: number;
  allDonation: number;
  allMember: number;
  allHospital: number;
}
