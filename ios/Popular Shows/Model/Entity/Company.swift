//
//  Company.swift
//  Popular Shows
//
//  Created by Fatih Sen on 22.12.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct Company: Codable, Equatable {

  public static let empty = Company() // TODO implement empty

  public func copy() -> Company {
    return self // TODO implement logic
  }

  public static func == (lhs: Company, rhs: Company) -> Bool {
    return false // TODO implement equatable logic
  }  
}