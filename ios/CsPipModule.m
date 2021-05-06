
//
//  PiPSupportModule.m
//  labs2020mobile
//
//  Created by Oleksii Skliarenko on 11.02.2021.
//


#import "CsPipModule.h"
#import <AVKit/AVKit.h>

@implementation CsPiPModule

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(isPiPSupported:(RCTPromiseResolveBlock)resolve
                  withRejecter:(RCTPromiseRejectBlock)reject) {
  BOOL isPiPSupported = [AVPictureInPictureController isPictureInPictureSupported];
  resolve(@(isPiPSupported));
}
@end

