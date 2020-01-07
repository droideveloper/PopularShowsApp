//
//  Season.swift
//  Popular Shows
//
//  Created by Fatih Sen on 22.12.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct Season: Codable, Equatable {

  public static let empty = Season() // TODO implement empty

  public func copy() -> Season {
    return self // TODO implement logic
  }

  public static func == (lhs: Season, rhs: Season) -> Bool {
    return false // TODO implement equatable logic
  }  
}