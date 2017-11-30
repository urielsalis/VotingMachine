package me.urielsalis.voting.controllers

import me.urielsalis.voting.Voting
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("vote")
class VoteController {
    @GetMapping("/getVotes")
    fun getVotes(@RequestParam candidate: Int) = Voting.instance.getVotesFromCandidate(candidate)


}