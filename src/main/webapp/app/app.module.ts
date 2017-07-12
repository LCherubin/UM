import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { MathFinEcoUniversitySharedModule, UserRouteAccessService } from './shared';
import { MathFinEcoUniversityHomeModule } from './home/home.module';
import { MathFinEcoUniversityAdminModule } from './admin/admin.module';
import { MathFinEcoUniversityAccountModule } from './account/account.module';
import { MathFinEcoUniversityEntityModule } from './entities/entity.module';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        MathFinEcoUniversitySharedModule,
        MathFinEcoUniversityHomeModule,
        MathFinEcoUniversityAdminModule,
        MathFinEcoUniversityAccountModule,
        MathFinEcoUniversityEntityModule
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class MathFinEcoUniversityAppModule {}
