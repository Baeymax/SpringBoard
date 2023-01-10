package test.springboard.service;

import test.springboard.domain.Board;
import test.springboard.repository.BoardRepository;

public class BoardService {
    private final BoardRepository boardRepository;

    public  BoardService(BoardRepository boardRepository) { this.boardRepository = boardRepository; }

    public Long CreateBoard(Board board) {
        boardRepository.save(board);
        return board.getBoardNo();
    }

}
