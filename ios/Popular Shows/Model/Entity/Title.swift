//
//  Title.swift
//  Popular Shows
//
//  Created by Fatih Sen on 22.12.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct Title: Codable, Equatable {

  public static let empty = Title() // TODO implement empty

  public func copy() -> Title {
    return self // TODO implement logic
  }

  public static func == (lhs: Title, rhs: Title) -> Bool {
    return false // TODO implement equatable logic
  }  
}