import Foundation
import CryptoKit

@objc public class DataResult: NSObject {
    
    @objc public private(set) var success: Data?
    @objc public private(set) var failure: Error?
    
    private init(_ success: Data?, _ failure: Error?) {
        super.init()
        self.success = success
        self.failure = failure
    }
    
    public convenience init(success: Data) {
        self.init(success, nil)
    }
    public convenience init(failure: Error) {
        self.init(nil, failure)
    }
    public convenience init(_ block: () throws -> Data) {
        do {
            let data = try block()
            self.init(success: data)
        } catch {
            self.init(failure: error)
        }
    }
}

@objc public class CryptoKitWrapper : NSObject {
    
    @objc public class func encrypt(key: Data, nonce: Data, plaintext: Data) -> DataResult {
        DataResult {
            let symetricKey = SymmetricKey(data: key)
            let aesNonce: AES.GCM.Nonce = try AES.GCM.Nonce(data: nonce)
            let sealedBox: AES.GCM.SealedBox = try AES.GCM.seal(plaintext, using: symetricKey, nonce: aesNonce)
            
            return sealedBox.ciphertext + sealedBox.tag
        }
    }
 
    @objc public class func decrypt(key: Data, nonce: Data, ciphertextAndTag: Data) -> DataResult {
        DataResult {
            let tagIndex = ciphertextAndTag.endIndex - 16
            let ciphertext = ciphertextAndTag[ciphertextAndTag.startIndex ..< tagIndex]
            let tag = ciphertextAndTag[tagIndex ..< ciphertextAndTag.endIndex]
 
            let symKey = SymmetricKey(data: key)
            let aesNonce: AES.GCM.Nonce = try AES.GCM.Nonce(data: nonce)
            let sealedBox: AES.GCM.SealedBox = try AES.GCM.SealedBox(nonce: aesNonce, ciphertext: ciphertext, tag: tag)
            return try AES.GCM.open(sealedBox, using: symKey)
        }
    }
}
