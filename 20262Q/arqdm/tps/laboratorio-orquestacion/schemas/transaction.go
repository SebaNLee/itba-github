package schemas

// Transaction States for 2PC Coordinator
type TransactionState string

const (
	TransactionStateInitiated TransactionState = "INITIATED"
	TransactionStatePreparing TransactionState = "PREPARING"
	TransactionStateCommitted TransactionState = "COMMITTED"
	TransactionStateAborted   TransactionState = "ABORTED"
	TransactionStateTimeout   TransactionState = "TIMEOUT"
)

// Participant States for 2PC Participants
type ParticipantState string

const (
	ParticipantStateIdle      ParticipantState = "IDLE"
	ParticipantStatePrepared  ParticipantState = "PREPARED"
	ParticipantStateCommitted ParticipantState = "COMMITTED"
	ParticipantStateAborted   ParticipantState = "ABORTED"
)

// Vote types for 2PC Protocol
type Vote string

const (
	VoteYes Vote = "YES"
	VoteNo  Vote = "NO"
)

// Decision types for 2PC Protocol
type Decision string

const (
	DecisionCommit Decision = "COMMIT"
	DecisionAbort  Decision = "ABORT"
)

// Service identifiers
type ServiceType string

const (
	ServiceSale      ServiceType = "sale"
	ServicePayment   ServiceType = "payment"
	ServiceInventory ServiceType = "inventory"
)
