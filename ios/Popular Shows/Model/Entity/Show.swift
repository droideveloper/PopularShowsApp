//
//  Show.swift
//  Popular Shows
//
//  Created by Fatih Sen on 22.12.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct Show: Codable, Equatable {

  public static let empty = Show() // TODO implement empty

  public func copy() -> Show {
    return self // TODO implement logic
  }

  public static func == (lhs: Show, rhs: Show) -> Bool {
    return false // TODO implement equatable logic
  }  
}