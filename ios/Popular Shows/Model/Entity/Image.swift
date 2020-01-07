//
//  Image.swift
//  Popular Shows
//
//  Created by Fatih Sen on 22.12.2019.
//  Copyright © 2019 Fatih Şen. All rights reserved.
//

import Foundation

public struct Image: Codable, Equatable {

  public static let empty = Image() // TODO implement empty

  public func copy() -> Image {
    return self // TODO implement logic
  }

  public static func == (lhs: Image, rhs: Image) -> Bool {
    return false // TODO implement equatable logic
  }  
}