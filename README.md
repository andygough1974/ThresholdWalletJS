# Threshold signatures
## Immediate Task
	Extract and encapsulate extant Java code into a usable form
	Probably Node.js
## Components
### EcdsaTwoPartyThresholdSignature
This project contains the heavy crypto lifting and is used in both App.java and in the Android app. 
It is a good candidate for porting to a managable language but I fear that debugging hard core cryptographic
code will be frustrating in the context of the Stanford event and will produce little visible progress
I would expose it initially in a REST interface so that I could progress the more visible parts of the project
but ultimately it will be a team decision
### App.java
This is the relevant key generation and sharing code taken from the Multibit sample
It needs to be rewritten in node
### BitcoinTwoFactorAuthAndroid
This is the mobile app that also uses the ECDSA stuff above, 
It exercises the second signing participant and it should be taken out of the Android context
and rewritten in node to reuse the ECDSA REST Api/library

## Demonstration
A simple JQM page or two pages to exercise the key sharing and signing processes
Key exchange is by QR in the demo
## Background
The process for each signer in a t of n scenario is identical
At the start there is an additional task to generate the key and distribute the shares
Here we have a 2 of 2 configuration, Bob and Alice

# Of Note
	threshold is more flexible than multisig
	it allows complex authentication policies
	it preserves anonymity
	the key itself is never revealed or generated on any one device
# Questions
	Are all shares equal or can I have say any four but then one from a certain (veto) group
	Can we demonstrate a t of n scenario in the time available?

# References
https://freedom-to-tinker.com/blog/stevenag/threshold-signatures-for-bitcoin-wallets-are-finally-here/
https://freedom-to-tinker.com/blog/stevenag/new-research-better-wallet-security-for-bitcoin/
http://www.cs.princeton.edu/~stevenag/threshold_sigs.pdf
https://github.com/citp/TwoFactorBtcWallet

# ThresholdWalletJS
