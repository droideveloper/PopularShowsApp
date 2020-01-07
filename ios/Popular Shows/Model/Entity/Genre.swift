//
//  Genre.swift
//  Popular Shows
//
//  Created by Fatih Sen on 22.12.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct Genre: Codable, Equatable {

  public static let empty = Genre() // TODO implement empty

  public func copy() -> Genre {
    return self // TODO implement logic
  }

  public static func == (lhs: Genre, rhs: Genre) -> Bool {
    return false // TODO implement equatable logic
  }  
}