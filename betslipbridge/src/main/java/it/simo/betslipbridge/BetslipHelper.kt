package it.simo.betslipbridge

// TODOLIST
//  [ ] How send the info of outcome selected?
//      -> A flow?
//      -> A callback?

/**
 *
 * It provides a mocked env for all the outcomes selected
 *
 *
 */
object BetslipHelper {

    // This will be called at the start of the section where the outcomes are
    fun isOutcomeSelected(): Boolean {
        return false
    }

    fun notifyOutcomeSelected() {
        // With a shared flow?
    }
}