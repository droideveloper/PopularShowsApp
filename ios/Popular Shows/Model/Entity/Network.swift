//
//  Network.swift
//  Popular Shows
//
//  Created by Fatih Sen on 22.12.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct Network: Codable, Equatable {

  public static let empty = Network() // TODO implement empty

  public func copy() -> Network {
    return self // TODO implement logic
  }

  public static func == (lhs: Network, rhs: Network) -> Bool {
    return false // TODO implement equatable logic
  }  
}