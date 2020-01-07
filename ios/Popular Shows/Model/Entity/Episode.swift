//
//  Episode.swift
//  Popular Shows
//
//  Created by Fatih Sen on 22.12.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct Episode: Codable, Equatable {

  public static let empty = Episode() // TODO implement empty

  public func copy() -> Episode {
    return self // TODO implement logic
  }

  public static func == (lhs: Episode, rhs: Episode) -> Bool {
    return false // TODO implement equatable logic
  }  
}