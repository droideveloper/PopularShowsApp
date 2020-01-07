//
//  ShowExtra.swift
//  Popular Shows
//
//  Created by Fatih Sen on 22.12.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct ShowExtra: Codable, Equatable {

  public static let empty = ShowExtra() // TODO implement empty

  public func copy() -> ShowExtra {
    return self // TODO implement logic
  }

  public static func == (lhs: ShowExtra, rhs: ShowExtra) -> Bool {
    return false // TODO implement equatable logic
  }  
}