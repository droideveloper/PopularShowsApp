//
//  Credit.swift
//  Popular Shows
//
//  Created by Fatih Sen on 22.12.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct Credit: Codable, Equatable {

  public static let empty = Credit() // TODO implement empty

  public func copy() -> Credit {
    return self // TODO implement logic
  }

  public static func == (lhs: Credit, rhs: Credit) -> Bool {
    return false // TODO implement equatable logic
  }  
}