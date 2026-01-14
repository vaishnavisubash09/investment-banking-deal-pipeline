import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CdkDragDrop, DragDropModule, transferArrayItem } from '@angular/cdk/drag-drop';
import { DEAL_STAGES, DealStage } from '../../models/deal.enums';
import { Deal } from '../../models/deal.model';
import { DealService } from '../../services/deal.service';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  imports: [DragDropModule, CommonModule],
  templateUrl: './deal-pipeline.page.html',
  styleUrls: ['./deal-pipeline.page.scss']
})
export class DealPipelinePage {

  stages = DEAL_STAGES;

  dealsByStage: Record<DealStage, Deal[]> = {
    PROSPECT: [],
    UNDER_EVALUATION: [],
    TERM_SHEET_SUBMITTED: [],
    CLOSED: [],
    LOST: []
  };

  constructor(
    private route: ActivatedRoute,
    private dealService: DealService,
    private router: Router
  ) {
    // 🔥 Single source of truth: resolver
    this.route.data.subscribe(({ deals }) => {
      this.resetBoard();

      (deals as Deal[]).forEach(deal => {
        const stage = deal.currentStage as DealStage;

        if (this.dealsByStage[stage]) {
          this.dealsByStage[stage].push(deal);
        } else {
          console.warn('Unknown stage from backend:', deal.currentStage);
          this.dealsByStage['PROSPECT'].push(deal);
        }
      });
    });
  }

  private resetBoard() {
    this.stages.forEach(s => this.dealsByStage[s] = []);
  }

  drop(event: CdkDragDrop<Deal[]>, newStage: DealStage) {
    if (event.previousContainer === event.container) return;

    const deal = event.previousContainer.data[event.previousIndex];

    this.dealService.updateStage(deal.id, { stage: newStage })
      .subscribe(updated => {

        // 🔥 Remove from all columns
        this.stages.forEach(s => {
          this.dealsByStage[s] = this.dealsByStage[s].filter(d => d.id !== deal.id);
        });

        // 🔥 Insert updated deal into correct column
        this.dealsByStage[updated.currentStage].push(updated);
      });
  }

  openDeal(deal: Deal) {
    this.router.navigate(['/deals', deal.id]);
  }
}
