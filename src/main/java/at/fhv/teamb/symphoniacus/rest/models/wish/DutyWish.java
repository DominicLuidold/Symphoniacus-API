package at.fhv.teamb.symphoniacus.rest.models.wish;

import java.util.ArrayList;
import java.util.List;


public class DutyWish {
    private Integer dutyId;
    private List<Integer> musicalPieces;

    public DutyWish(Integer dutyId) {
        this.dutyId = dutyId;
    }

    /**
     * Add a muscialPiece to a Dutywish.
     *
     * @param musicalPieceId to add
     */
    public void addMusicalPiece(Integer musicalPieceId) {
        if (musicalPieces == null) {
            musicalPieces = new ArrayList<>();
        }
        musicalPieces.add(musicalPieceId);
    }

}
