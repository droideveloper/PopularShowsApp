//
//  Video.swift
//  Popular Shows
//
//  Created by Fatih Sen on 22.12.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct Video: Codable, Equatable {

  public static let empty = Video() // TODO implement empty

  public func copy() -> Video {
    return self // TODO implement logic
  }

  public static func == (lhs: Video, rhs: Video) -> Bool {
    return false // TODO implement equatable logic
  }  
}